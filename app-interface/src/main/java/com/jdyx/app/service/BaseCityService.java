package com.jdyx.app.service;

import com.jdyx.app.bean.BaseCity;

import java.util.List;

public interface BaseCityService  {
    List<BaseCity> getCityByProvinceId(String provinceId);
}
