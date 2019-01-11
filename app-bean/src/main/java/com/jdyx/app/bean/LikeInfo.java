package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:like_info表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-02
 */
@Data
public class LikeInfo implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long likeId;

    /**
     * 短视频id
     */
    private Integer videoId;

    /**
     * 短视频所属人id
     */
    private Integer userId;

    /**
     * 点赞人id
     */
    private Integer followedId;

    /**
     * 点赞人姓名
     */
    private String follName;

    /**
     * 关注日期
     */
    private Date followedDate;

    /**
     * 是否点赞
     */
    private Integer isLike=1;
}