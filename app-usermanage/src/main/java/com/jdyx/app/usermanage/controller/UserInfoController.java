package com.jdyx.app.usermanage.controller;

import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String cc(@CookieValue(name=Const.SSO_COOKIE_NAME,required = false) String cookieValue,String token ,HttpServletRequest request, HttpServletResponse response) {
        //先判断token是否为空
        if(!StringUtil.isEmpty(token) && cookieValue == null){
            //只要这个参数有,说明验证登录通过
            //添加到cookie
            Cookie cookie = new Cookie(Const.SSO_COOKIE_NAME,token);
            System.out.println(cookie);
            //设置cookie限定的路径 "/"代表当前网站的那一层都可以
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        //1.登录过了
            if(!StringUtil.isEmpty(token)){
                //验证cookie是否失效
                //登录成功返回之前的页面
            }else{
                //token为空 是第一次访问，则需要返回到登录页面
//               UserInfo login =  userInfoService.login();
            }
            //2.没有登录过则去登录页面

        return "";
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
    @RequestMapping("/getSmsCode")
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
    @RequestMapping("/requestLogin")
    public Object login(String phone, String code,HttpServletRequest request, HttpServletResponse response) {
         return userInfoService.login(phone,code,request,response);
    }



}
