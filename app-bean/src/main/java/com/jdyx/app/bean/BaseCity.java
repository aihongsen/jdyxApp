package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 描述:base_city表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-11
 */
@Data
public class BaseCity {
    /**
     * 市级id
     */
    @TableId(type = IdType.AUTO)
    private Integer cityId;

    /**
     * 
     */
    private String cityCode;

    /**
     * 
     */
    private String cityName;

    /**
     * 
     */
    private String provinceCode;

   
}