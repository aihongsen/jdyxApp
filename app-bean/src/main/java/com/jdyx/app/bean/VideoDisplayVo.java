package com.jdyx.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VideoDisplayVo  implements Serializable{

    /**
     * 编号
     */
    private Integer videoId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 短视频类型 0代表才艺、1代表技能
     */
    private Integer releaseType;

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
    private String musicAddress;

    /**
     * 视频点赞数
     */
    private Integer videoLikes;

    /**
     * 视频浏览量
     */
    private Integer videoViews;

    /**
     * 视频分享量
     */
    private Integer videoShare;

    /**
     * 视频关注度
     */
    private Integer videoFollow;

    /**
     * 岗位id
     */
    private Integer jobId;

    /**
     * 视频发布日期
     */
    private Date videoDate;
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

    /**
     * 用户头像
     */
    private String imageSrc;

    /**
     * 用户名称
     */
    private String userName;

}
