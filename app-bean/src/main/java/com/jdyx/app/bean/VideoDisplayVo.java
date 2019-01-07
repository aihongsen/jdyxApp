package com.jdyx.app.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VideoDisplayVo extends VideoDisplay {
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 距离
     */
    private String distance;
}
