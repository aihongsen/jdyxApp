package com.jdyx.app.appvideo.controller;

import com.jdyx.app.bean.Positioning;
import com.jdyx.app.service.PositioningService;
import com.jdyx.app.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * @return
     */
    @RequestMapping(value = "/savePositioning",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="保存定位")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "latitude", value = "经度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "longitude", value = "纬度", required = false, dataType = "String")
    })
    public Map<String,String> savePositioning(Integer userId, BigDecimal latitude,BigDecimal longitude){
        if(latitude==null||longitude==null){
            return  ResultUtil.exceptionMap("403","没有找到定位信息");
        }
        try {
            positioningService.savePositioning(new Positioning(userId,latitude,longitude));
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }
}
