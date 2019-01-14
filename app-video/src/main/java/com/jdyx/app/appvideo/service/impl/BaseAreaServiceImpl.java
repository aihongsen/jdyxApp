package com.jdyx.app.appvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdyx.app.appvideo.mapper.BaseAreaMapper;
import com.jdyx.app.bean.BaseArea;
import com.jdyx.app.service.BaseAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAreaServiceImpl implements BaseAreaService {
    @Autowired
    BaseAreaMapper baseAreaMapper;
    @Override
    public List<BaseArea> getAreaByCityId(String cityCode) {
        return baseAreaMapper.selectList(new QueryWrapper<BaseArea>().eq("city_code",cityCode));
    }
}
