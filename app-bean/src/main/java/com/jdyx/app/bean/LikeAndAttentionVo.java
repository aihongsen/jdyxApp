package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LikeAndAttentionVo implements Serializable{

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
     * 是否点赞
     */
    private Integer isLike;

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
     * 是否关注
     */
    private Integer isAttention;

    /**
     * 岗位id
     */
    private Integer jobId;

    /**
     * 岗位名称
     */
    private String jobName;

    /**
     * 视频是否删除 默认是0展示、1代表逻辑删除
     */
    private Integer isDelete;

    /**
     * 视频发布日期
     */
    private Date videoDate;

    /**
     * 是否违规 后台 对短视频可控下线 0正常、1违规
     */
    private Integer isViolation ;

    /**
     * 是否隐藏 个人 对短视频可控隐藏 0不隐藏、1隐藏
     */
    private Integer isHidden ;

    /**
     * 用户头像
     */
    private String imageSrc;

    /**
     * 用户名称
     */
    private String userName;
}
