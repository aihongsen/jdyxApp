package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 描述:base_area表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-11
 */
@Data
public class BaseArea {
    /**
     * 县级id
     */
    @TableId(type = IdType.AUTO)
    private Integer areaId;

    /**
     * 
     */
    private String areaCode;

    /**
     * 
     */
    private String areaName;

    /**
     * 
     */
    private String cityCode;

   
}