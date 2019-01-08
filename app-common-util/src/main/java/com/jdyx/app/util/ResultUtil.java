package com.jdyx.app.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

    public static Map<String,String> successMap(String data){
        HashMap<String, String> map = new HashMap<>();
        map.put("data",data);
        map.put("code","200");
        map.put("message","success");
        return map;
    }
    public static Map<String,String> errorMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("code","500");
        map.put("message","服务器异常");
        return map;
    }

    public static Map<String,String> exceptionMap(String code,String message){
        HashMap<String, String> map = new HashMap<>();
        map.put("code",code);
        map.put("message",message);
        return map;
    }
}
