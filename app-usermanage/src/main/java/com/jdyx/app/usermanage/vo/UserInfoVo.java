package com.jdyx.app.usermanage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserInfoVo implements Serializable {
    /**
     * 编号
     */
    private Integer id;

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
    private String birthday;

    /**
     * 学历 0代表高中以下、1代表大专、2代表本科、3代表本科以上
     */
    private String education;

    /**
     * 工作年限 0代表1年以下、1代表1年以上、2代表2年以上、3代表3年以上、4代表4年以上、5代表5至10年以上
     */
    private String workYear;

    /**
     *  期望岗位
     */
    private List<ExpectationPostVo> expPostId;


}
