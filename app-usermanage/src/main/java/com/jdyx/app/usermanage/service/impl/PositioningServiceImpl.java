package com.jdyx.app.usermanage.service.impl;

import com.jdyx.app.bean.Positioning;
import com.jdyx.app.service.PositioningService;
import com.jdyx.app.usermanage.mapper.PositioningMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PositioningServiceImpl implements PositioningService {
    @Autowired
    PositioningMapper positioningMapper;

    @Override
    public void savePositioning(Positioning positioning) {

    }

    @Override
    public int insert(Positioning positioning) {
        return positioningMapper.insert(positioning);
    }
}
