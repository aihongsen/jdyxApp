package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:user_info表的实体类
 */
@Data
public class UserInfo  implements Serializable{
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号类别
     */
    private Integer accountType;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 注册日期
     */
    private Date createDate;

    /**
     * 账号是否开启 0代表未开启、1代表已开启
     */
    private String isEnable;

    /**
     * 微信账号
     */
    private String wachatAccount;

    /**
     * 是否已实名 0代表未实名制、1代表已实名制
     */
    private String isCertification;

    /**
     * 用户头像
     */
    private String imageSrc;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 0代表男、1代表女
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 学历 0代表高中以下、1代表大专、2代表本科、3代表本科以上
     */
    private String education;

    /**
     * 工作年限 0代表1年以下、1代表1年以上、2代表2年以上、3代表3年以上、4代表4年以上、5代表5至10年以上
     */
    private String workYear;

    /**
     * 身份证号
     */
    private String cartid;

    /**
     * 是否启用消息通知 用户APP端识别是否接收广播
     */
    private String isNotice;

    /**
     * 是否启用免打扰 设置免消息打扰
     */
    private String isDisturb;

    /**
     * 当前状态 0代表空闲、1代表忙碌
     */
    private String currentStatus;

    /**
     * 邀请人ID 识别邀请人是谁
     */
    private Integer inviteId;

    /**
     * 免费支付次数 免费视频通话次数
     */
    private Integer free;

    /**
     * 账户金额 钱包余额
     */
    private BigDecimal balance;

    /**
     * 设备ID 当前系统表关联
     */
    private Integer softId;
}