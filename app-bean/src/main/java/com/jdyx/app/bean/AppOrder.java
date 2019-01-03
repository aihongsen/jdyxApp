package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:app_order表的实体类
 */
@Data
public class AppOrder implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单交易编号
     */
    private String transactionId;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 订单详情 1、购买视频、2购买信息(购买类型)
     */
    private String orderDescription;

    /**
     * 卖家ID 关联视频的第一帧图片
     */
    private Integer sellerId;

    /**
     * 交易金额 本次交易实质金额
     */
    private BigDecimal transactionAmount;

    /**
     * 订单状态 0交易中、1交易成功、2交易失败、3已取消、4已超时
     */
    private String orderStatus;

    /**
     * 发生时间 创建订单号 就生成
     */
    private Date orderTime;

    /**
     * 微信交易编号 微信
     */
    private String alipayTradeNo;

    /**
     * 回调时间 微信
     */
    private Date callbackTime;

    /**
     * 回调信息 微信
     */
    private String callbackContent;
}