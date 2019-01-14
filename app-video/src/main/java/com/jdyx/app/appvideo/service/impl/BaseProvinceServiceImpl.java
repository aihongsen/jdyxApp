package com.jdyx.app.appvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdyx.app.appvideo.mapper.BaseProvinceMapper;
import com.jdyx.app.bean.BaseProvince;
import com.jdyx.app.service.BaseProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseProvinceServiceImpl implements BaseProvinceService {

    @Autowired
    BaseProvinceMapper baseProvinceMapper;

    @Override
    public List<BaseProvince> getAllProvince() {
        return baseProvinceMapper.selectList(new QueryWrapper<BaseProvince>());
    }
}
