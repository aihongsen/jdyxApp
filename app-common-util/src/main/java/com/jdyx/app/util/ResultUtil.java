package com.jdyx.app.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

    public static Object successMap(Object data){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("code","200");
        map.put("message","success");
        return map;
    }
    public static Object errorMap(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code","500");
        map.put("message","服务器异常");
        return map;
    }

    public static Object exceptionMap(String code,String message){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("message",message);
        return map;
    }
}
