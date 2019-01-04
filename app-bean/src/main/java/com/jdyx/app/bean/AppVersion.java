package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述:app_version表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-03
 */
@Data
public class AppVersion implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备类别 0代表安卓、1代表苹果
     */
    private String dvType;

    /**
     * 最新版本号 软件版本号做更新使用
     */
    private String appVersion;

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
     * 设备类别 0代表安卓、1代表苹果
     * @return dv_type 设备类别 0代表安卓、1代表苹果
     */
    public String getDvType() {
        return dvType;
    }

    /**
     * 设备类别 0代表安卓、1代表苹果
     * @param dvType 设备类别 0代表安卓、1代表苹果
     */
    public void setDvType(String dvType) {
        this.dvType = dvType == null ? null : dvType.trim();
    }

    /**
     * 最新版本号 软件版本号做更新使用
     * @return app_version 最新版本号 软件版本号做更新使用
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 最新版本号 软件版本号做更新使用
     * @param appVersion 最新版本号 软件版本号做更新使用
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }
}