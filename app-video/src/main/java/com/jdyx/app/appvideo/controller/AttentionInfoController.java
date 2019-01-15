package com.jdyx.app.appvideo.controller;

import com.jdyx.app.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/attention")
@Controller
public class AttentionInfoController {

    @RequestMapping("/getAttentionInfoByUserId")
    @ResponseBody
    public Object getAttentionInfoByUserId(){

        return ResultUtil.successMap("");
    }

    @RequestMapping(value = "/saveAttention",method = RequestMethod.POST)
    @ResponseBody
    public Object saveAttentionInfo(){

        return ResultUtil.successMap("");
    }

}
