package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:account_details表的实体类
 */
@Data
public class AccountDetails implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer accountId;

    /**
     * 流水类型 0代表收益、1代表消费、2代表充值、2代表提现
     */
    private Integer recordType;

    /**
     * 流水描述 例如对流水类型描述 例如视频通话消费
     */
    private String description;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 发生交易金额 流水金额
     */
    private BigDecimal transactionAmount;

    /**
     * 发生交易日期 流水记录日期
     */
    private Date transactionDate;

    /**
     * 当前状态 交易中、交易成功、交易失败
     */
    private String tradingStatus;

    /**
     * 是否可见 该条记录是否显示(系统操作是否显示)
     */
    private String isDisplay;

}