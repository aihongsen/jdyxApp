package com.jdyx.app.usermanage.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.HttpRequest;
import com.aliyuncs.http.HttpResponse;
import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.usermanage.mapper.UserInfoMapper;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> findAll() {
        BigDecimal bigDecimal = new BigDecimal(100);
        System.out.println(bigDecimal.getClass());
        return userInfoMapper.selectList(null);

    }

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

    @Override
    public UserInfo getUserByPhone(UserInfo userInfo) {
        return userInfoMapper.getUserByPhone(userInfo);
    }

    @Override
    public Integer createUser(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    /**
     * 登录
     * @param phone
     * @param code
     * @return
     */
    @Override
    public Object login(String phone, String code) {
        Map<String,Object> result = new HashMap<String,Object>();
        String trim_phone = phone.trim();
        String trim_code = code.trim();
        //1.判断手机号格式
        if(trim_phone.length()<11 || trim_phone.length()>11||phone.length()<11||phone.length()>11 ||phone == null || phone == "") {
            result.put("error", Const.LOGIN_CODE_ERROR);   //输入的格式错误
            return result;
        }else {
            //1.1判断是否是有效的数字
            for (int i = 0; i < phone.length(); i++) {
                if(!Character.isDigit(phone.charAt(i))) {
                    result.put("error",Const.LOGIN_CODE_ERROR);   //输入的格式错误
                    return result;
                }
            }
            //★先查缓存数据
            Jedis jedis = new Jedis(Const.SERVER_ADDRESS,Const.SERVER_REDIS_PORT);
            //拼接字符串
            String key = Const.PHONE_PREFIX+phone+Const.PHONE_SUFFIX;
            String server_key = jedis.get(key);
            //关闭jedis
            jedis.close();
            //验证
            if(!code.equals(server_key)){
                //验证未通过
                result.put("error", Const.LOGIN_CODE_TIMEOUT);   //验证码错误或超时
            }else {
                //验证码通过，在验证数据库里是否有这个手机号
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(phone);
                //判断用户是注册还是登陆
                if (getUserByPhone(userInfo) == null){
                    Date date = new Date();
                    //注册用户
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
                //用户登陆
                //信息添加到cookie中
                Cookie userId = new Cookie("user_id", "1");
                // 1.制证
                String token = UUID.randomUUID().toString().replaceAll("-","");
                //保存到redis中


                //有cookie的情况下
                //在检查cookie的合法性和时效性

                //2.交给这个人


                result.put("success", true);
                //在验证数据库里是否有这个手机号
                //userService.query();
            }
            //2.通过验证调用服务查询 手机号是否存在
            try {
                //创建一个map给业务层传参使用
                Map<String,Object> paramMap = new HashMap<String,Object>();
                paramMap.put("phone", trim_phone);
//					UserInfo user= userInfoService.(paramMap);
                //成功
                result.put("success", true);
            } catch (Exception e) {
                //没有查到则为新注册用户
                result.put("success",false);
                result.put("error", Const.LOGIN_PHONE_ERROR);   //当前用户不存在
            }
            return result;
        }
    }

}
