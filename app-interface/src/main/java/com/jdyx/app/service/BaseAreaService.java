package com.jdyx.app.service;

import com.jdyx.app.bean.BaseArea;

import java.util.List;

public interface BaseAreaService {
    List<BaseArea> getAreaByCityId(String cityCode);
}
