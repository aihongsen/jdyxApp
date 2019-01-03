package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.service.VideoDisplayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/video")
@Controller
public class VideoDisplayController {

    @Autowired
    VideoDisplayService videoDisplayService;

    @RequestMapping("/getAllVideoDisplay")
    @ResponseBody
    public String getAllVideoDisplay(){
        List<VideoDisplay> allVideoDisplay = videoDisplayService.getAllVideoDisplay();
        Object o = JSON.toJSON(allVideoDisplay);
        log.info("objectJSON : {}",o);
        String json = String.valueOf(JSON.toJSON(allVideoDisplay));
        log.info("StringJson : {}",json);
        return  json;
    }

    @RequestMapping("/getAllVideoDisplayById")
    @ResponseBody
    public String getAllVideoDisplayById(HttpRequest request){

        List<VideoDisplay> allVideoDisplay = videoDisplayService.getAllVideoDisplay();
        log.info("获取该用户所有视频 ： {}",allVideoDisplay);
        String json = JSON.toJSON(allVideoDisplay).toString();
        return  json;
    }

    @RequestMapping("/saveVideoDisplay")
    @ResponseBody
    public String saveVideoDisplay(VideoDisplay videoDisplay,HttpServletRequest request ){
        Map<String,String> userInfo = (Map<String,String>)request.getAttribute("userInfo");
        int id ;
        if(userInfo == null){
            id=1;
            log.info("用户信息错误");
        }else{
            id = Integer.parseInt(userInfo.get("id"));
            log.info("用户为：{}",userInfo);
        }

        videoDisplay.setVideoDate(new Date());
        videoDisplay.setUserId(id);

        videoDisplayService.saveVideoDisplay(videoDisplay);
        return "OK";
    }


}
