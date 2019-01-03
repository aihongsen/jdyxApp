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
	public static final Integer PHONE_REDIS_CODE_RUNTIME =30000;		//手机验证码超时时间5分钟
	public static final String LOGIN_PHONE_ERROR = "用户不存在";	//登录错误提示  用户不存在
	public static final String REQUEST_ERROR = "请求出错";	//登录错误提示  请求出错
	public static final String LOGIN_CODE_TIMEOUT = "验证码错误或超时";	//验证码错误或超时
	public static final String LOGIN_CODE_ERROR = "输入的格式错误";	//登录错误提示  输入的格式错误
	public static final String SSO_COOKIE_NAME = "appsso";	//登录监测域名常量(生产环境根据域名在做修改)
}
