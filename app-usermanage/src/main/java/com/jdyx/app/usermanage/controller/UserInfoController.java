package com.jdyx.app.usermanage.controller;

import com.jdyx.app.bean.UserInfo;
import com.jdyx.app.service.UserInfoService;
import com.jdyx.app.usermanage.vo.UserInfoVo;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.*;

@Controller
@Api(value = "用户模块")
@RequestMapping("user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 测试方法
     */

    @ResponseBody
    @RequestMapping(value = "/text",method = RequestMethod.POST)
    @ApiOperation(value="做测试使用")
//    @ApiOperation(value="测试方法", notes="无参数")
    public String cc() throws Exception {
        //通过 在到数据库去查
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("1580133293");
        Date date = new Date();
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
        userInfo.setId(null);                //ID
        userInfo.setCreateDate(date);        //注册日期
        userInfo.setAccountType(0);          //账号类型
        userInfo.setIsEnable("0");           //账号是否开启
        userInfo.setIsCertification("0");    //是否完成实名认证 0未完成
        jedis.close();
        return token;
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
     * @param userInfo
     * @return result
     */
    @ResponseBody
    @ApiOperation(value="请求短信验证码(已完成)", notes="根据手机获取验证码")
    @RequestMapping(value = "/getSmsCode",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam( name = "userInfo", value = "用户信息类", required = true, dataType = "UserInfo")
    })

    public Object requestVerification(@RequestBody UserInfo userInfo){
        return userInfoService.requestVerification(userInfo.getPhone());
    }

    /**
     * 请求登录验证
     * @param phone
     * @param code
     * @return result
     */
    @ResponseBody		//通过HttpConverterMessage进行消息转换。
    @ApiOperation(value="请求登录验证(已完成)", notes="验证登录、完成注册")
    @RequestMapping(value = "/requestLogin",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "code", value = "短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deviceId", value = "设备ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "latitude", value = "经度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "longitude", value = "纬度", required = false, dataType = "String")
    })
    public Object login(String phone, String code, String deviceId, BigDecimal latitude, BigDecimal longitude) {
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
    @ApiOperation(value="个人信息填写(进行中)", notes="信息填写")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "用户ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "image_src", value = "头像地址", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "gender", value = "性别", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "birthday", value = "生日", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "education", value = "学历", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "workYear", value = "工作年限", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "expPostId[]", value = "学历", required = true, dataType = "array[]"),
    })
    @RequestMapping(value = "/personalInfo",method = RequestMethod.POST)
    public Object personalInfo(UserInfoVo userInfoVo){
        HashMap<Object, Object> result = new HashMap<>();
        //1.获取参数校验
            //1.1判断用户是否存在
//            if(userInfoVo.getId() == null || userInfoVo.getPhone() ==null || userInfoVo.getName()== null || userInfoVo.getImageSrc()== null
//            || userInfoVo.getGender() == null || userInfoVo.getBirthday() == null ||userInfoVo.getEducation() == null || userInfoVo.getWorkYear() == null ||
//                    userInfoVo.getExpPostId() == null
//            ){
//                result.put("code",400);
//                result.put("message","请确认信息是否完全填写再次提交");
//                return result;
//            }
            if(userInfoVo.getName() == null){
                 result.put("code",400);
                 result.put("message","请确认信息是否完全填写再次提交");
            }
            //2.效验姓名
            for (int i = 0; i < userInfoVo.getName().length(); i++) {
                        if(!(userInfoVo.getName().charAt(i) >= 0x4E00 && userInfoVo.getName().charAt(i) <= 0x9FA5)){
                            //不是中文
                            result.put("code",400);
                            result.put("error","您输入的姓名不是中文");

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
