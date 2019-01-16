package com.jdyx.app.appvideo.controller;

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

import java.util.List;

@Slf4j
@RequestMapping("/positioning")
@Controller
public class PositioningController {

//    @Autowired
//    PositioningService positioningService;
//
//    @Autowired
//    BaseProvinceService baseProvinceService;
//    @Autowired
//    BaseCityService baseCityService;
//    @Autowired
//    BaseAreaService baseAreaService;
//
//    @RequestMapping(value = "/getAllProvince",method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value="获取省份信息")
//    public Object getAllProvince(){
//        try {
//            List<BaseProvince> allProvince = baseProvinceService.getAllProvince();
//            return ResultUtil.successMap(allProvince);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultUtil.errorMap();
//        }
//    }
//
//    @RequestMapping(value = "/getCityByProvinceId",method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value="根据provinceCode获取城市信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "provinceCode", value = "省份code", required = true, dataType = "String")
//    })
//    public Object getCityByProvinceCode(String provinceCode){
//        if (StringUtil.isEmpty(provinceCode)){
//            return ResultUtil.exceptionMap(2019,"provinceCode无效");
//        }
//        try {
//            List<BaseCity> citys = baseCityService.getCityByProvinceId(provinceCode);
//            return ResultUtil.successMap(citys);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultUtil.errorMap();
//        }
//    }
//
//    @RequestMapping(value = "/getAreaByCityCode",method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value="根据cityCode获取城市信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "cityCode", value = "城市code", required = true, dataType = "String")
//    })
//    public Object getAreaByCityCode(String cityCode){
//        if (StringUtil.isEmpty(cityCode)){
//            return ResultUtil.exceptionMap(2019,"cityCode无效");
//        }
//        try {
//            List<BaseArea> areas = baseAreaService.getAreaByCityId(cityCode);
//            return ResultUtil.successMap(areas);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultUtil.errorMap();
//        }
//    }

}
