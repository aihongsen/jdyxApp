package com.jdyx.app.usermanage.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jdyx.app.bean.Positioning;
import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.usermanage.mapper.PositioningMapper;
import com.jdyx.app.usermanage.mapper.UserInfoMapper;
import com.jdyx.app.util.*;
import com.jdyx.app.vo.EntitiesVo;
import com.sun.xml.internal.ws.server.sei.ValueGetter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;

import static com.jdyx.app.util.NetUtil.registerHunXinAccount;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PositioningMapper positioningMapper;

    /**
     * 根据用户ID获取用户信息
     * @param id
     * @return
     */
    @Override
    public UserInfo getUserById(Integer id) {
        return userInfoMapper.selectById(id);
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        return userInfoMapper.selectList(null);

    }

    /**
     * 根据手机号获取信息
     * @param
     * @return
     */
    @Override
    public UserInfo getUserByPhone(String phone) {
        return userInfoMapper.getUserByPhone(phone);
    }

    /**
     * 退出登录
     * @param token
     * @param phone
     * @return
     */
    @Override
    public Object logOut(String token, String phone) {
        HashMap<String, Object> result = new HashMap<>();
        if(!token.isEmpty() && token.length()>6){
            Jedis jedis = RedisUtil.getJedis();
            String tokenName = Const.TOKEN_PREFIX + phone+Const.TOKEN_SUFFIX;
            String server_token = jedis.get(tokenName);
            if (server_token == null){
                result.put("code",400);
                result.put("message","当前未登录或已登录超时");
                return result;
            }
            jedis.close();
            if (server_token.equals(token)){
                Jedis jedisv = RedisUtil.getJedis();
                Long del = jedisv.del(tokenName);
                jedisv.close();
                result.put("code",200);
                result.put("message","");
                return result;
            }
        }
        result.put("code",400);
        result.put("message","当前未登录或已登录超时");
        return result;
    }

    /**


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
            result.put("code",100);
            result.put("message","请输入有效的手机号码");      //格式错误
            return result;
        }else {
            //1.1在判断是否是有效的数字
            for (int i = 0; i < phone.length(); i++) {
                if(!Character.isDigit(phone.charAt(i))) {
                    result.put("code",100);
                    result.put("message","请输入有效的手机号码");   //格式错误
                    return result;
                }
            }
            //1.2在判断是否是手机号
            if(!StringUtil.isMobile(phone)){
                result.put("code",100);
                result.put("message","请输入有效的手机号码");
                return result;
            }
            //2.格式验证通过，调用阿里短信平台获取验证码
            //2.1随机生成4位随机码
            int vcode = (int)((Math.random()*9+1)*1000);
            //调用Jedis
//            Jedis jedis = new Jedis(Const.SERVER_ADDRESS,Const.SERVER_REDIS_PORT);
            Jedis jedis = RedisUtil.getJedis();
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
                result.put("message","");
            } catch (ClientException e) {
                //请求失败
                result.put("code",112);
                result.put("message","请求验证码超时");    //请求出错
            }
        }
        return result;
    }

    /**
     * 自动登录
     * @param token
     * @param phone
     * @return
     */
    @Override
    public Boolean autoLogin( String phone,String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!token.isEmpty()) {
            Jedis jedis = RedisUtil.getJedis();
            String tokenName = Const.TOKEN_PREFIX + phone + Const.TOKEN_SUFFIX;
            String servertoken = jedis.get(tokenName);
            jedis.close();
            if (token.equals(servertoken)) {
                return true;
            }
        }
        return  false;
    }

    /**
     * 检查验证码
     * @param phone
     * @param code
     * @return
     */
    @Override
    public Boolean examinationCode(String phone, String code) {
        //获取redis
        Jedis jedis = RedisUtil.getJedis();
        String key = Const.PHONE_PREFIX + phone + Const.PHONE_SUFFIX;
        String server_key = jedis.get(key);
        jedis.close();
        //校验
        if(code.equals(server_key)) {
            return true;
        }
        return false;
    }

    /**
     * 登录
     * @param phone
     * @param code
     * @param deviceId
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    public Object login(String phone, String code,String deviceId,BigDecimal latitude,BigDecimal longitude,String token) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        //1.先判断使用token是否存在并是否符合自动登录
        if (phone != null && token !=null){
            if(autoLogin(phone, token)){
                //登录成功返回状态
                UserInfo user = userInfoMapper.getUserByPhone(phone);
                if(user != null){
                    Integer id = user.getId();
                    UserInfo info = userInfoMapper.selectById(id);
                    HashMap<String, Object> redata = new HashMap<>();
                    redata.put("hx_name", info.getConnectionName());
                    redata.put("password", Const.HUNXIN_PASSWORD);
                    redata.put("user_id", info.getId());
                    redata.put("phone", info.getPhone());
                    redata.put("nickname", info.getConnectionNickname());
                    if(user.getName()== null || info.getGender() == null || info.getBirthday() == null || info.getEducation() ==null || info.getWorkYear() == null || info.getWorkYear() == null){
                        redata.put("inf_status",0);   //未填写全信息
                    }else {
                        redata.put("inf_status",1);   //已填写全信息
                    }
                    log.info("用户☆☆{}☆☆自动登录成功,时间{}",info.getPhone(),new Date().toString());
                    return ResultUtil.successMap(redata);
                }
            }
        }

        //2.登录、注册
            //2.1过滤格式
        //排格式
        //手机号
        if (phone == null || phone.trim().length()<11 || phone.trim().length()>11) {

            return ResultUtil.exceptionMap(100,"请输入有效的手机号码");
        }
        //判断手机号是否是数字
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return ResultUtil.exceptionMap(100,"请输入有效的手机号码");
            }
        }
        //验证码
        if(code.isEmpty()){
            return ResultUtil.exceptionMap(110,"验证码不能为空");
        }

        //校验验证码
        if(!examinationCode(phone,code)){
            return ResultUtil.exceptionMap(111,"验证码错误，请尝试重新获取再次登录");
        }
        //区分 新用户 or 老用户
        if(userInfoMapper.getUserByPhone(phone) == null){
            //新用户 为注册
            UserInfo newUser = new UserInfo();
            Date date = new Date();
            newUser.setId(null);                //ID
            newUser.setPhone(phone);            //手机号
            newUser.setCreateDate(date);        //注册日期
            newUser.setAccountType(0);          //账号类型
            newUser.setIsEnable("0");           //账号是否开启
            newUser.setIsCertification("0");    //是否完成实名认证 0未完成
            newUser.setIsNotice("0");           //是否接受广播 0默认开启
            newUser.setIsDisturb("0");          //是否启用消息免打扰
            newUser.setCurrentStatus("0");      //当前状态 0是空闲
            newUser.setFree(0);                 //免费支付次数
            BigDecimal balance = new BigDecimal(0);
            newUser.setBalance(balance);        //账号初始金额0元
            newUser.setDeviceId(deviceId);      //设备ID
            //拼接环信信息
                HashMap<String, Object> entitieMap = new HashMap<>();
                String a = UUID.randomUUID().toString().replaceAll("-", "");
                String username = a.substring(0,11) + phone + a.substring(20,32);
                String nickname = a.substring(0,6);
                entitieMap.put("username",username);
                entitieMap.put("password",Const.HUNXIN_PASSWORD);
                entitieMap.put("nickname",nickname);
                //注册环信账号
                EntitiesVo hunXinAccount = (EntitiesVo) registerHunXinAccount(entitieMap);
                newUser.setConnectionUuid(hunXinAccount.getUuid());                //对应环信账号UUID
                newUser.setConnectionName(hunXinAccount.getUsername());            //对应环信账号UUID
                newUser.setConnectionNickname(hunXinAccount.getNickname());        //对应环信账号UUID
                //注册APP账号
                 Integer user = createUser(newUser);
                 if(user<0){
                    //注册失败
                     return  ResultUtil.exceptionMap(120,"注册APP账号失败");
                 }else{
                     log.info("用户☆☆{}☆☆注册成功,时间{}",phone,new Date().toString());
                 }

        }
            //老用户
                //获取用户信息
                UserInfo userAAA = getUserByPhone(phone);
                Integer id = userAAA.getId();
                UserInfo oldUser = getUserById(id);
                //登录成功记录坐标
                if(latitude != null && longitude != null){
                    Positioning positioning = new Positioning();
                    positioning.setUserId(oldUser.getId());
                    positioning.setLatitude(latitude);
                    positioning.setLongitude(longitude);
                    //判断坐标表记录是否存在
                    QueryWrapper<Positioning> queryWrapper = new QueryWrapper<Positioning>().eq("user_id",oldUser.getId());
                    if(positioningMapper.selectOne(queryWrapper) ==null){
                        //没有记录插入
                        if (positioningMapper.insert(positioning) <=0){
                            log.info("插入坐标失败",oldUser.getId());
                        }
                    }else {
                        //有记录更新记录
                        if(positioningMapper.update(positioning,queryWrapper) <=0){
                            log.info("根据用户ID更新记录失败",oldUser.getId());
                        }
                    }
                }
        //返回信息
            HashMap<String, Object> data = new HashMap<>();
            data.put("hx_name",oldUser.getConnectionName());
            data.put("password",Const.HUNXIN_PASSWORD);
            data.put("user_id",oldUser.getId());
            data.put("phone",oldUser.getPhone());
            data.put("nickname",oldUser.getConnectionNickname());
            //拼接token串
            String random = UUID.randomUUID().toString().replaceAll("-", "");
            String tokenString = random.substring(10, 31);
            String tokenName = Const.TOKEN_PREFIX+oldUser.getPhone()+Const.TOKEN_SUFFIX;
            Jedis jedis_token = RedisUtil.getJedis();
            //保存token
            String mes = jedis_token.setex(tokenName, Const.TOKEN_PREFIX_CODE_RUNTIME, tokenString);
            jedis_token.close();
            data.put("token",tokenString);
            //判断用户是否完善了个人信息
            if(oldUser.getName()== null || oldUser.getGender() == null || oldUser.getBirthday() == null ||
               oldUser.getEducation() ==null || oldUser.getWorkYear() == null || oldUser.getWorkYear() == null){
                data.put("inf_status",0);   //未填写全信息
            }else {
                data.put("inf_status",1);   //已填写全信息
            }
        log.info("用户☆☆{}☆☆登录成功,时间{}",phone,new Date().toString());
        return ResultUtil.successMap(data);

    }


}
