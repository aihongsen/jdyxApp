package com.jdyx.app.usermanage.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.usermanage.mapper.UserInfoMapper;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> findAll() {
        BigDecimal bigDecimal = new BigDecimal(100);
        return userInfoMapper.selectList(null);

    }

    @Override
    public UserInfo getUserByPhone(UserInfo userInfo) {
        return userInfoMapper.getUserByPhone(userInfo);
    }

    @Override
    public Integer createUser(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    /**
     * 请求验证码
     * @param phone
     * @return
     */
    @Override
    public Object requestVerification(String phone) {
        Map<String,Object> result = new HashMap<String,Object>();
        //1.判断手机号格式
        if(phone.length()<11||phone.length()>11 ||phone == null || phone == "") {
            result.put("error", Const.LOGIN_CODE_ERROR);      //格式错误
            return result;
        }else {
            //1.1在判断是否是有效的数字
            for (int i = 0; i < phone.length(); i++) {
                if(!Character.isDigit(phone.charAt(i))) {
                    result.put("error",Const.LOGIN_CODE_ERROR);   //格式错误
                    return result;
                }
            }
            //2.格式验证通过，调用阿里短信平台获取验证码
            //2.1随机生成4位随机码
            int vcode = (int)((Math.random()*9+1)*1000);
            //调用Jedis
            Jedis jedis = new Jedis(Const.SERVER_ADDRESS,Const.SERVER_REDIS_PORT);
            //拼接字符串
            String key = Const.PHONE_PREFIX+phone+Const.PHONE_SUFFIX;
            //发送验证码
            try {
                //调用短信工具类
                SmsUtils.sendSms(phone,vcode);
                //请求成功将手机号和验证码保存到redis中
                String code = String.valueOf(vcode);
                //讲验证码保存到redis中
                jedis.setex(key, Const.PHONE_REDIS_CODE_RUNTIME, code);
                //关闭Jedis
                jedis.close();
                //返回消息给前端
                result.put("success",true);
            } catch (ClientException e) {
                //请求失败
                result.put("success",false);
                result.put("error",Const.REQUEST_ERROR);    //请求出错
            }
        }
        return result;
    }

    /**
     * 登录
     * @param phone
     * @param code
     * @return
     */
    @Override
    public Object login(String phone, String code, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (phone == null || code == null  || phone.trim().length()<11 || phone.trim().length()>11){
            //判断手机号是否是数字
            for (int i = 0; i < phone.length(); i++) {
                if (!Character.isDigit(phone.charAt(i))) {
                    result.put("success", "false");
                    result.put("error", Const.LOGIN_CODE_ERROR);   //输入的格式错误
                    return result;
                }
            }
            //去缓存比对验证码
            Jedis jedis = new Jedis(Const.SERVER_ADDRESS,Const.SERVER_REDIS_PORT);
            String key = Const.PHONE_PREFIX + phone + Const.PHONE_SUFFIX;
            String server_key = jedis.get(key);
            jedis.close();
            //比对
            if(!code.equals(server_key)) {
                //异常
                result.put("error", Const.LOGIN_CODE_TIMEOUT);   //验证码错误或超时
                result.put("success","false");
                return result;
            }else{
                //通过 在到数据库去查
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(phone);
                //区分是老用户还是新注册用户
                UserInfo user = getUserByPhone(userInfo);
                if(user == null) {
                    //新用户 注册账号
                    Date date = new Date();
                    userInfo.setId(null);
                    userInfo.setCreateDate(date);
                    userInfo.setAccountType(0);
                    userInfo.setIsEnable("0");
                    userInfo.setIsCertification("0");
                    userInfo.setIsNotice("0");
                    userInfo.setIsDisturb("0");
                    userInfo.setCurrentStatus("0");
                    userInfo.setFree(0);
                    BigDecimal balance = new BigDecimal(0);
                    userInfo.setBalance(balance);
                    userInfo.setCreateDate(date);
                    if (createUser(userInfo)>0){
                        //注册成功
                        result.put("message","注册成功");
                        result.put("success", true);
                    }else{
                        //注册失败
                        result.put("message","注册失败");
                        result.put("success", false);
                        return  result;
                    }

                }
                //登录操作
                Integer userId = user.getId();
                Cookie phone_id = new Cookie("id", userId.toString());
                Cookie phone_no = new Cookie("phone", user.getPhone());
                String token = UUID.randomUUID().toString().replaceAll("-","");
                Cookie phone_token = new Cookie("token",token);
                response.addCookie(phone_id);
            }



        }else{  //没登录过
            //验证

        }
        return result;
    }
}
