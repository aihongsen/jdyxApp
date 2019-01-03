package com.jdyx.app.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:user_info表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-03
 */
public class UserInfo {
    /**
     * 编号
     */
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
     * 是否启用消息通知 用户APP端识别是否接收广播 0代表启用，1代表未启用
     */
    private String isNotice;

    /**
     * 是否启用免打扰 设置免消息打扰 0代表未开启，1代表开启
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
     * 软件名 软件版本
     */
    private String appId;

    /**
     * 设备id 识别设备型号
     */
    private String deviceId;

    /**
     * 编号
     * @return id 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 编号
     * @param id 编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 账号类别 0代表普通用户，1代表管理用户，2代表系统用户
     * @return account_type 账号类别 0代表普通用户，1代表管理用户，2代表系统用户
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 账号类别 0代表普通用户，1代表管理用户，2代表系统用户
     * @param accountType 账号类别 0代表普通用户，1代表管理用户，2代表系统用户
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 手机号
     * @return phone 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机号
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 支付密码
     * @return pay_password 支付密码
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 支付密码
     * @param payPassword 支付密码
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**
     * 注册日期
     * @return create_date 注册日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 注册日期
     * @param createDate 注册日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 账号是否开启 0代表未开启、1代表已开启
     * @return is_enable 账号是否开启 0代表未开启、1代表已开启
     */
    public String getIsEnable() {
        return isEnable;
    }

    /**
     * 账号是否开启 0代表未开启、1代表已开启
     * @param isEnable 账号是否开启 0代表未开启、1代表已开启
     */
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }

    /**
     * 微信账号
     * @return wachat_account 微信账号
     */
    public String getWachatAccount() {
        return wachatAccount;
    }

    /**
     * 微信账号
     * @param wachatAccount 微信账号
     */
    public void setWachatAccount(String wachatAccount) {
        this.wachatAccount = wachatAccount == null ? null : wachatAccount.trim();
    }

    /**
     * 是否已实名 0代表未实名制、1代表已实名制
     * @return is_certification 是否已实名 0代表未实名制、1代表已实名制
     */
    public String getIsCertification() {
        return isCertification;
    }

    /**
     * 是否已实名 0代表未实名制、1代表已实名制
     * @param isCertification 是否已实名 0代表未实名制、1代表已实名制
     */
    public void setIsCertification(String isCertification) {
        this.isCertification = isCertification == null ? null : isCertification.trim();
    }

    /**
     * 用户头像
     * @return image_src 用户头像
     */
    public String getImageSrc() {
        return imageSrc;
    }

    /**
     * 用户头像
     * @param imageSrc 用户头像
     */
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc == null ? null : imageSrc.trim();
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 性别 0代表男、1代表女
     * @return gender 性别 0代表男、1代表女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 性别 0代表男、1代表女
     * @param gender 性别 0代表男、1代表女
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * 出生日期
     * @return birthday 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 出生日期
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 学历 0代表高中以下、1代表大专、2代表本科、3代表本科以上
     * @return education 学历 0代表高中以下、1代表大专、2代表本科、3代表本科以上
     */
    public String getEducation() {
        return education;
    }

    /**
     * 学历 0代表高中以下、1代表大专、2代表本科、3代表本科以上
     * @param education 学历 0代表高中以下、1代表大专、2代表本科、3代表本科以上
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * 工作年限 0代表1年以下、1代表1年以上、2代表2年以上、3代表3年以上、4代表4年以上、5代表5至10年以上
     * @return work_year 工作年限 0代表1年以下、1代表1年以上、2代表2年以上、3代表3年以上、4代表4年以上、5代表5至10年以上
     */
    public String getWorkYear() {
        return workYear;
    }

    /**
     * 工作年限 0代表1年以下、1代表1年以上、2代表2年以上、3代表3年以上、4代表4年以上、5代表5至10年以上
     * @param workYear 工作年限 0代表1年以下、1代表1年以上、2代表2年以上、3代表3年以上、4代表4年以上、5代表5至10年以上
     */
    public void setWorkYear(String workYear) {
        this.workYear = workYear == null ? null : workYear.trim();
    }

    /**
     * 身份证号
     * @return cartid 身份证号
     */
    public String getCartid() {
        return cartid;
    }

    /**
     * 身份证号
     * @param cartid 身份证号
     */
    public void setCartid(String cartid) {
        this.cartid = cartid == null ? null : cartid.trim();
    }

    /**
     * 是否启用消息通知 用户APP端识别是否接收广播 0代表启用，1代表未启用
     * @return is_notice 是否启用消息通知 用户APP端识别是否接收广播 0代表启用，1代表未启用
     */
    public String getIsNotice() {
        return isNotice;
    }

    /**
     * 是否启用消息通知 用户APP端识别是否接收广播 0代表启用，1代表未启用
     * @param isNotice 是否启用消息通知 用户APP端识别是否接收广播 0代表启用，1代表未启用
     */
    public void setIsNotice(String isNotice) {
        this.isNotice = isNotice == null ? null : isNotice.trim();
    }

    /**
     * 是否启用免打扰 设置免消息打扰 0代表未开启，1代表开启
     * @return is_disturb 是否启用免打扰 设置免消息打扰 0代表未开启，1代表开启
     */
    public String getIsDisturb() {
        return isDisturb;
    }

    /**
     * 是否启用免打扰 设置免消息打扰 0代表未开启，1代表开启
     * @param isDisturb 是否启用免打扰 设置免消息打扰 0代表未开启，1代表开启
     */
    public void setIsDisturb(String isDisturb) {
        this.isDisturb = isDisturb == null ? null : isDisturb.trim();
    }

    /**
     * 当前状态 0代表空闲、1代表忙碌
     * @return current_status 当前状态 0代表空闲、1代表忙碌
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * 当前状态 0代表空闲、1代表忙碌
     * @param currentStatus 当前状态 0代表空闲、1代表忙碌
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.trim();
    }

    /**
     * 邀请人ID 识别邀请人是谁
     * @return invite_id 邀请人ID 识别邀请人是谁
     */
    public Integer getInviteId() {
        return inviteId;
    }

    /**
     * 邀请人ID 识别邀请人是谁
     * @param inviteId 邀请人ID 识别邀请人是谁
     */
    public void setInviteId(Integer inviteId) {
        this.inviteId = inviteId;
    }

    /**
     * 免费支付次数 免费视频通话次数
     * @return free 免费支付次数 免费视频通话次数
     */
    public Integer getFree() {
        return free;
    }

    /**
     * 免费支付次数 免费视频通话次数
     * @param free 免费支付次数 免费视频通话次数
     */
    public void setFree(Integer free) {
        this.free = free;
    }

    /**
     * 账户金额 钱包余额
     * @return balance 账户金额 钱包余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 账户金额 钱包余额
     * @param balance 账户金额 钱包余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 软件名 软件版本
     * @return app_id 软件名 软件版本
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 软件名 软件版本
     * @param appId 软件名 软件版本
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 设备id 识别设备型号
     * @return device_id 设备id 识别设备型号
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设备id 识别设备型号
     * @param deviceId 设备id 识别设备型号
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }
}