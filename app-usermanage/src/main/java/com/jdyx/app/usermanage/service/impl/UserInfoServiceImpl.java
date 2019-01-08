package com.jdyx.app.usermanage.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.usermanage.mapper.UserInfoMapper;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.NetUtil;
import com.jdyx.app.util.SmsUtils;
import com.jdyx.app.util.StringUtil;
import com.jdyx.app.vo.EntitiesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getById(Integer id) {
        return userInfoMapper.selectById(id);
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        BigDecimal bigDecimal = new BigDecimal(100);
        return userInfoMapper.selectList(null);

    }

    /**
     * 根据手机号获取信息
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo getUserByPhone(UserInfo userInfo) {
        return userInfoMapper.getUserByPhone(userInfo);
    }

    /**
     * 创建用户
     * @param userInfo
     * @return
     */
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
            result.put("code",403);
            result.put("message", Const.LOGIN_CODE_ERROR);      //格式错误
            return result;
        }else {
            //1.1在判断是否是有效的数字
            for (int i = 0; i < phone.length(); i++) {
                if(!Character.isDigit(phone.charAt(i))) {
                    result.put("code",403);
                    result.put("message",Const.LOGIN_CODE_ERROR);   //格式错误
                    return result;
                }
            }
            //1.2在判断是否是手机号
            if(!StringUtil.isMobile(phone)){
                result.put("code",403);
                result.put("message","手机号错误");
                return result;
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
                result.put("code",200);
            } catch (ClientException e) {
                //请求失败
                result.put("code",403);
                result.put("message",Const.REQUEST_ERROR);    //请求出错
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
    public Object login(String phone, String code,String deviceId,String latitude,String longitude) {
        Map<String, Object> result = new HashMap<String, Object>();
        //排格式
        if (phone == null || code == null  || phone.trim().length()<11 || phone.trim().length()>11) {
            //判断手机号是否是数字
            for (int i = 0; i < phone.length(); i++) {
                if (!Character.isDigit(phone.charAt(i))) {
                    result.put("code",403);
                    result.put("message", Const.LOGIN_CODE_ERROR);   //输入的格式错误
                    return result;
                }
            }
        }
        //去缓存比对验证码
        Jedis jedis = new Jedis(Const.SERVER_ADDRESS,Const.SERVER_REDIS_PORT);
        String key = Const.PHONE_PREFIX + phone + Const.PHONE_SUFFIX;
        String server_key = jedis.get(key);
//        jedis.close();
        //比对
        if(!code.equals(server_key)) {
            //异常
            result.put("code",403);
            result.put("message", Const.LOGIN_CODE_TIMEOUT);   //验证码错误或超时
            return result;
        }
        //通过 在到数据库去查
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        //区分是老用户还是新注册用户
        UserInfo user = getUserByPhone(userInfo);
        //新用户注册
        if(user == null) {
            Date date = new Date();
            userInfo.setId(null);                //ID
            userInfo.setCreateDate(date);        //注册日期
            userInfo.setAccountType(0);          //账号类型
            userInfo.setIsEnable("0");           //账号是否开启
            userInfo.setIsCertification("0");    //是否完成实名认证 0未完成
            userInfo.setIsNotice("0");           //是否接受广播 0默认开启
            userInfo.setIsDisturb("0");          //是否启用消息免打扰
            userInfo.setCurrentStatus("0");      //当前状态 0是空闲
            userInfo.setFree(0);                 //免费支付次数
            BigDecimal balance = new BigDecimal(0);
            userInfo.setBalance(balance);        //账号初始金额0元
            userInfo.setDeviceId(deviceId);      //设备ID
            //拼接环信信息
            HashMap<String, Object> map = new HashMap<>();
            String a = UUID.randomUUID().toString().replaceAll("-", "");
            String username = a.substring(0,11) + "15801332983" + a.substring(20,32);
            String nickname = a.substring(0,6);
            map.put("username",username);
            map.put("password",Const.HUNXIN_PASSWORD);
            map.put("nickname",nickname);
            try {
                //注册环信账号
                EntitiesVo hunXinAccount = (EntitiesVo)NetUtil.registerHunXinAccount(map);
                userInfo.setConnectionUuid(hunXinAccount.getUuid());        //对应环信账号UUID
                userInfo.setConnectionName(hunXinAccount.getUsername());        //对应环信账号UUID
                userInfo.setConnectionNickname(hunXinAccount.getNickname());        //对应环信账号UUID
                //注册成功  则在创建App账号
                System.out.println(hunXinAccount);
                if (createUser(userInfo)<=0){
                    //注册失败
                    result.put("code",503);
                    result.put("message","注册失败，服务器出错");
                    return  result;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //根据用户信息 创建返回数据
        //根据用户信息
        Integer id = user.getId();
        UserInfo info = userInfoMapper.selectById(id);
        if(info != null){
            HashMap<String, Object> data = new HashMap<>();
//            data.put("token",token);
            data.put("openid",info.getConnectionName());
            data.put("password",Const.HUNXIN_PASSWORD);
            if(info.getName()== null || info.getGender() == null || info.getBirthday() == null || info.getEducation() ==null || info.getWorkYear() == null || info.getWorkYear() == null){
                data.put("inf_status",0);   //未填写全信息
            }else {
                data.put("inf_status",1);   //已填写全信息
            }
            result.put("code",200);
            result.put("data",data);
        }else {
            result.put("code",408);
            result.put("message","请求超时");
        }
        return result;

    }


}
