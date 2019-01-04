package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:video_display表的实体类
 */
@Data
public class VideoDisplay implements Serializable{
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 短视频类型 0代表才艺、1代表技能
     */
    private String releaseType;

    /**
     * 视频标题
     */
    private String videoTitle;

    /**
     * 视频描述
     */
    private String videoDescription;

    /**
     * 视频封面图片
     */
    private String videoPicture;

    /**
     * 原视频地址
     */
    private String videoAddress;

    /**
     * 加水印后视频地址
     */
    private String videoWatermarkAddress;

    /**
     * 音乐地址
     */
    private String musicDdress;

    /**
     * 岗位id
     */
    private Integer jobId;

    /**
     * 视频是否删除 默认是0展示、1代表逻辑删除
     */
    @TableLogic
    private String isDelete;

    /**
     * 视频发布日期
     */
    private Date videoDate;

    /**
     * 是否违规 后台 对短视频可控下线 0正常、1违规
     */
    private String isViolation = "0";

    /**
     * 是否隐藏 个人 对短视频可控隐藏 0不隐藏、1隐藏
     */
    private String isHidden = "0";
}