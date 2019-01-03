package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述:soft_version表的实体类
 */
@Data
public class SoftVersion implements Serializable {
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
    private String dvVersion;
}