package com.jdyx.app.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeInfoVo implements Serializable {

    /**
     * 编号
     */
    private Integer videoId;
    /**
     * 视频点赞数
     */
    private Integer videoLikes;

    /**
     * 是否点赞
     */
    private Integer isLike;
}
