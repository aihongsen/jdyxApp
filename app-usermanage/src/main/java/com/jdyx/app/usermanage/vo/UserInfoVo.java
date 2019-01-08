package com.jdyx.app.usermanage.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.util.Date;

public class UserInfoVo {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号类别 0代表普通用户，1代表管理用户，2代表系统用户
     */
    private Integer accountType;

    /**
     * 手机号
     */
    private String phone;

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
     * 设备id 识别设备型号
     */
    private String deviceId;

}
