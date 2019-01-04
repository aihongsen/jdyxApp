package com.jdyx.app.appvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdyx.app.appvideo.mapper.PositioningMapper;
import com.jdyx.app.bean.Positioning;
import com.jdyx.app.service.PositioningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PositioningServiceImpl implements PositioningService{

    @Autowired
    PositioningMapper positioningMapper;
    @Override
    public void savePositioning(Positioning positioning) {
        QueryWrapper<Positioning> queryWrapper = new QueryWrapper<Positioning>().eq("user_id", positioning.getUserId());
        Positioning selectOne = positioningMapper.selectOne(queryWrapper);
        if (selectOne ==null){
            log.info("第一次登陆，添加坐标");
            positioningMapper.insert(positioning);
        }else {
            log.info("更新坐标");
            positioningMapper.update(positioning,queryWrapper);
        }

    }
}
