package com.jdyx.app.usermanage.controller;

import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.ObjectUtils;
import com.jdyx.app.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 测试方法
     */
    @ResponseBody
    @RequestMapping("/cc")
    public String cc() throws Exception {
        //通过 在到数据库去查
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("1580133293");
            Date date = new Date();
            userInfo.setId(null);                //ID
            userInfo.setCreateDate(date);        //注册日期
            userInfo.setAccountType(0);          //账号类型
            userInfo.setIsEnable("0");           //账号是否开启
            userInfo.setIsCertification("0");    //是否完成实名认证 0未完成
        String a = UUID.randomUUID().toString().replaceAll("-", "");
        String token_key = a.substring(0,4) + "15801332983" + a.substring(28,32);
        Jedis jedis = new Jedis(Const.SERVER_ADDRESS,Const.SERVER_REDIS_PORT);
        //保存到redis里
        String token = "token_info:"+token_key;
        System.out.println(token);
//        Map<String, Object> map = ObjectUtils.objectToMap(userInfo);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("name","肖芳");
        ObjectUtils.setObject(jedis,token,map);
        //保存到redis里
        jedis.close();
        return token;
    }


    /**
     * 测试方法
     */
    @ResponseBody
    @RequestMapping("/findAll")
    public List<UserInfo> getAll() {
        return userInfoService.findAll();
    }

    /**
     * 请求短信验证码
     * @param phone
     * @return result
     */
    @ResponseBody
    @RequestMapping(value = "/getSmsCode",method = RequestMethod.POST)
    public Object  requestVerification(String phone) {
        return userInfoService.requestVerification(phone);
    }

    /**
     * 请求登录验证
     * @param phone
     * @param code
     * @return result
     */
    @ResponseBody		//通过HttpConverterMessage进行消息转换。
    @RequestMapping(value = "/requestLogin",method = RequestMethod.POST)
    public Object login(String phone, String code,String deviceId,String latitude,String longitude) {
         return userInfoService.login(phone, code,deviceId,latitude,longitude);
    }

    /**
     * 个人信息完善资料
     * @param name 用户姓名
     * @param image_src  用户头像
     * @param gender    用户性别
     * @param birthday 用户生日
     * @param education 用户学历
     * @param workYear  用户工作年限
     * @param expPostId 用户期待岗位[]
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/personalInfo",method = RequestMethod.POST)
    public Object personalInfo(String token,Integer id,String name,String image_src,String gender,Date birthday,String education,String workYear,String expPostId[]){
        HashMap<Object, Object> result = new HashMap<>();
        System.out.println(name.length());
        //1.获取参数校验
            //姓名
            for (int i = 0; i < name.length(); i++) {
                        if(!(name.charAt(i) >= 0x4E00 && name.charAt(i) <= 0x9FA5)){
                            //不是中文
                            System.out.println("您输入的姓名不是中文");
                            result.put("error","您输入的姓名不是中文");

                        }
            }
        //2.判断用户是否存在
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);             //姓名
        userInfo.setGender(gender);         //性别
        userInfo.setImageSrc(image_src);    //头像
        userInfo.setBirthday(birthday);     //生日
        userInfo.setEducation(education);   //学历
        userInfo.setWorkYear(workYear);     //工作年限          //期望岗位在关联
        //2.插入数据
//            userInfoService.insertPersonalInfo(userInfo);

            return result;
    }

}
