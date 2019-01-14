package com.jdyx.app.appvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdyx.app.appvideo.mapper.BaseCityMapper;
import com.jdyx.app.bean.BaseCity;
import com.jdyx.app.service.BaseCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseCityServiceImpl implements BaseCityService {

    @Autowired
    BaseCityMapper baseCityMapper;
    @Override
    public List<BaseCity> getCityByProvinceId(String provinceCode) {
        return baseCityMapper.selectList(new QueryWrapper<BaseCity>().eq("province_code",provinceCode));
    }
}
