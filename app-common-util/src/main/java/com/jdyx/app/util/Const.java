package com.jdyx.app.util;

/**
 * 常量类
 * @author aihs
 *
 */
public class Const {
	public static final String SERVER_ADDRESS="192.168.0.20";	//服务器地址
	public static final Integer SERVER_REDIS_PORT=6379;			//Redis服务器端口
	public static final String PHONE_PREFIX ="phoneno:";		//手机号前缀
	public static final String PHONE_SUFFIX =":code";		//手机号后缀
	public static final Integer PHONE_REDIS_CODE_RUNTIME =300;		//手机验证码超时时间5分钟
	public static final String LOGIN_PHONE_ERROR = "用户不存在";	//登录错误提示  用户不存在
	public static final String REQUEST_ERROR = "请求出错";	//登录错误提示  请求出错
	public static final String LOGIN_CODE_TIMEOUT = "验证码错误或超时";	//验证码错误或超时
	public static final String LOGIN_CODE_ERROR = "格式错误";	//登录错误提示  输入的格式错误
	public static final String SSO_COOKIE_NAME = "appsso";	//登录监测域名常量(生产环境根据域名在做修改)
	public static final String APP_REQUEST_URL = "https://a1.easemob.com/1169180327177665/jingdianyixian/users";
	public static final String APP_KEY = "1169180327177665#jingdianyixian";
	public static final String APP_CLIENT_ID = "YXA60TaMwDGoEeiXB--hZWNm8w";
	public static final String APP_CLIENT_SECRET = "YXA6jVWh9I2-Zie2l3gBi29Bp5NxXmo";
	public static final String HUNXIN_PASSWORD = "Jdyx12345678";
	public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";	//正则手机号

	public static final String ACCESS_KEY = "mrspbXHzeXCXjq-dqfimKxVG_3ZUH2_PnKm97TKS";//七牛云账号的SK
	public static final String SECRET_KEY = "YmarNqrqapJpSuCYEfnOKxSNjEIUNJ42Iy23Ebas";//七牛云账号的SK
	public static final String BUCKET_NAME = "default"; //七牛空间名称
	public static final String QINIU_ADRESS = "http://v.jdyxqq.com/"; //七牛空间地址开头
	public static final String PICTURE_URL = "http://v.jdyxqq.com/jdyx.gif";//水印地址
	public static final String PIPELINE = "default";//七牛云转码的队列
	public static final String LIKE_VIDEO="like:video:";
	public static final String WATCH_VIDEO="watch:video:";

}
