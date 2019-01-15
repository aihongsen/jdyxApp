package com.jdyx.app.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeAndAttentionVo implements Serializable{


    /**
     * 岗位名称
     */
    private String jobName;
    /**
     * 视频点赞数
     */
    private Integer videoLikes;
    /**
     * 是否点赞
     */
    private Integer isLike;
    /**
     * 是否关注
     */
    private Integer isAttention;
    /**
     * 视频分享量
     */
    private Integer videoShare;


}
