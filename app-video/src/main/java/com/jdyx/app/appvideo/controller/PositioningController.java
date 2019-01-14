package com.jdyx.app.appvideo.controller;

import com.jdyx.app.bean.BaseArea;
import com.jdyx.app.bean.BaseCity;
import com.jdyx.app.bean.BaseProvince;
import com.jdyx.app.bean.Positioning;
import com.jdyx.app.service.BaseAreaService;
import com.jdyx.app.service.BaseCityService;
import com.jdyx.app.service.BaseProvinceService;
import com.jdyx.app.service.PositioningService;
import com.jdyx.app.util.ResultUtil;
import com.jdyx.app.util.StringUtil;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/positioning")
@Controller
public class PositioningController {

    @Autowired
    PositioningService positioningService;

    @Autowired
    BaseProvinceService baseProvinceService;
    @Autowired
    BaseCityService baseCityService;
    @Autowired
    BaseAreaService baseAreaService;

    @RequestMapping(value = "/getAllProvince",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="获取省份信息")
    public Object getAllProvince(){
        try {
            List<BaseProvince> allProvince = baseProvinceService.getAllProvince();
            return ResultUtil.successMap(allProvince);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorMap();
        }
    }

    @RequestMapping(value = "/getCityByProvinceId",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="根据provinceCode获取城市信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "provinceCode", value = "省份code", required = true, dataType = "String")
    })
    public Object getCityByProvinceCode(String provinceCode){
        if (StringUtil.isEmpty(provinceCode)){
            return ResultUtil.exceptionMap(2019,"provinceCode无效");
        }
        try {
            List<BaseCity> citys = baseCityService.getCityByProvinceId(provinceCode);
            return ResultUtil.successMap(citys);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorMap();
        }
    }

    @RequestMapping(value = "/getAreaByCityCode",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="根据cityCode获取城市信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "cityCode", value = "城市code", required = true, dataType = "String")
    })
    public Object getAreaByCityCode(String cityCode){
        if (StringUtil.isEmpty(cityCode)){
            return ResultUtil.exceptionMap(2019,"cityCode无效");
        }
        try {
            List<BaseArea> areas = baseAreaService.getAreaByCityId(cityCode);
            return ResultUtil.successMap(areas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorMap();
        }
    }

    /**
     *
     0芍药居,39.9778107025,116.4361524582
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
    public Object savePositioning(Integer userId, BigDecimal latitude,BigDecimal longitude){

        if(userId==null ||userId ==0){
            return  ResultUtil.exceptionMap(2019,"用户id无效");
        }
        if(latitude==null||longitude==null||BigDecimal.ZERO.equals(latitude)||BigDecimal.ZERO.equals(longitude)){
            return  ResultUtil.exceptionMap(2019,"定位信息无效");
        }
        try {
            positioningService.savePositioning(new Positioning(userId,latitude,longitude));
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("保存定位失败 ",e);
            return ResultUtil.errorMap();
        }
    }
}
