package com.jdyx.app.appvideo.controller;

import com.jdyx.app.bean.Positioning;
import com.jdyx.app.service.PositioningService;
import com.jdyx.app.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RequestMapping("/positioning")
@Controller
public class PositioningController {

    @Autowired
    PositioningService positioningService;

    /**
     * 0芍药居,39.9778107025,116.4361524582
     1安贞门,39.9769803325,116.4059722424
     2知春路,39.9764705954,116.3399469852
     3知春里,39.9763143849,116.3286226988
     * @param positioning
     * @return
     */
    @RequestMapping("/savePositioning")
    @ResponseBody
    public Map<String,String> savePositioning(Positioning positioning){
        try {
            positioningService.savePositioning(positioning);
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }
}
