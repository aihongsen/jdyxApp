package com.jdyx.app.appvideo.controller;

import com.jdyx.app.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LIkeInfoController {

    @RequestMapping("/getLIkeInfoByUserId")
    @ResponseBody
    public Object getLIkeInfoByUserId(){

        return ResultUtil.successMap("");
    }

    @RequestMapping(value = "/saveLIkeInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object saveLIkeInfo(){

        return ResultUtil.successMap("");
    }
}
