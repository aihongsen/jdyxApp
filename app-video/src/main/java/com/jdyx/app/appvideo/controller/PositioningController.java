package com.jdyx.app.appvideo.controller;

import com.jdyx.app.bean.Positioning;
import com.jdyx.app.service.PositioningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Slf4j
@RequestMapping("/positioning")
@Controller
public class PositioningController {

    @Autowired
    PositioningService positioningService;

    @RequestMapping("/savePositioning")
    @ResponseBody
    public String savePositioning(Integer userId, String longitude,String latitude){

        Positioning positioning = new Positioning();
        positioning.setUserId(userId);
        positioning.setLongitude(new BigDecimal(longitude));
        positioning.setLatitude(new BigDecimal(latitude));
        positioningService.savePositioning(positioning);
        return "OK";
    }
}
