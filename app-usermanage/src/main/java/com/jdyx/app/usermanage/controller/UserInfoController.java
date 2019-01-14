package com.jdyx.app.usermanage.controller;
import com.jdyx.app.bean.Positioning;
import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.PositioningService;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.usermanage.vo.UserInfoVo;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.RedisUtil;
import com.jdyx.app.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
@Slf4j
@Controller
@Api(value = "用户模块")
@RequestMapping("user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PositioningService positioningService;
    /**
     * 测试方法
     */

    @ResponseBody
    @RequestMapping(value = "/text",method = RequestMethod.POST)
    @ApiOperation(value="做测试使用")
//    @ApiOperation(value="测试方法", notes="无参数")
    public String cc(String a) throws Exception {
        Date date = new Date();
        System.out.println(date);
        return null;
    }


    /**
     * 测试方法
     */
    @ResponseBody
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    @ApiOperation(value="获取所有用户信息(已完成)")
    public List<UserInfo> getAll() {
        return userInfoService.findAll();
    }

    /**
     * 请求短信验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSmsCode",method = RequestMethod.POST)
    public Object requestVerification(String phone){
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
    public Object login(String phone, String code, String deviceId, BigDecimal latitude, BigDecimal longitude,String token) throws IOException {
         return userInfoService.login(phone, code,deviceId,latitude,longitude,token);
    }

    /**
     * 退出登录
     */
    @ResponseBody		//通过HttpConverterMessage进行消息转换。
    @RequestMapping(value = "/logOut",method = RequestMethod.POST)
     public  Object logOut(String token,String phone){
        return userInfoService.logOut(token, phone);
     }

    /**
     * 自动登录
     */
    @ResponseBody		//通过HttpConverterMessage进行消息转换。
    @RequestMapping(value = "/autologin",method = RequestMethod.POST)
    public  Object autoLogin(String phone,String token){
        return userInfoService.autoLogin(phone,token);
    }

    /**
     * 填写基本信息
     * @param userInfoVo
     *      * @param name 用户姓名
     *      * @param image_src  用户头像
     *      * @param gender    用户性别
     *      * @param birthday 用户生日
     *      * @param education 用户学历
     *      * @param workYear  用户工作年限
     *      * @param expPostId 用户期待岗位[]
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/personalInfo",method = RequestMethod.POST)
    public Object personalInfo(@RequestBody UserInfoVo userInfoVo){
        System.out.println(userInfoVo.getName());
        HashMap<Object, Object> result = new HashMap<>();
        //1.获取参数校验
            //1.1判断用户是否存在
//            if(userInfoVo.getId() == null || userInfoVo.getPhone() ==null || userInfoVo.getName()== null || userInfoVo.getImageSrc()== null
//            || userInfoVo.getGender() == null || userInfoVo.getBirthday() == null ||userInfoVo.getEducation() == null || userInfoVo.getWorkYear() == null ||
//                    userInfoVo.getExpPostId() == null
//            ){
//                return ResultUtil.exceptionMap(130,"请确认信息是否完全填写再次提交");
//            }
//            if(userInfoVo.getName() == null){
//                return ResultUtil.exceptionMap(130,"请确认信息是否完全填写再次提交");
//            }
            //2.效验姓名
            for (int i = 0; i < userInfoVo.getName().length(); i++) {
                        if(!(userInfoVo.getName().charAt(i) >= 0x4E00 && userInfoVo.getName().charAt(i) <= 0x9FA5)){
                            //不是中文
                            return ResultUtil.exceptionMap(131,"您输入的姓名有误");
                        }
            }
            System.out.println(userInfoVo.getName());




//        userInfo.setName(name);             //姓名
//        userInfo.setGender(gender);         //性别
//        userInfo.setImageSrc(image_src);    //头像
//        userInfo.setBirthday(birthday);     //生日
//        userInfo.setEducation(education);   //学历
//        userInfo.setWorkYear(workYear);     //工作年限          //期望岗位在关联
        //2.插入数据
//            userInfoService.insertPersonalInfo(userInfo);
        //            //创建token
//            String u = UUID.randomUUID().toString().replaceAll("-", "");
//            String token_key = u.substring(0,4) + "15801332983" + u.substring(28,32);
//            String token = "token_info:"+token_key;

            return result;
    }

}
