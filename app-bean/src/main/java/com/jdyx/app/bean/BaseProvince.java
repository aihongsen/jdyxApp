package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 描述:base_province表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-11
 */
@Data
public class BaseProvince {
    /**
     * 省级id
     */
    @TableId(type = IdType.AUTO)
    private Integer provinceId;

    /**
     * 
     */
    private String provinceCode;

    /**
     * 
     */
    private String provinceName;

    
}