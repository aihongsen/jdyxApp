package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:attention_info表的实体类
 */
@Data
public class AttentionInfo implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 被关注人ID
     */
    private Integer userId;

    /**
     * 关注人ID
     */
    private Integer followedid;

    /**
     * 关注人头像地址
     */
    private String avatarSrc;

    /**
     * 关注人姓名
     */
    private String follName;

    /**
     * 关注日期
     */
    private Date followedDate;

    /**
     * 是否关注
     */
    private Integer isAttention;

    /**
     * 是否已读
     */
    private Integer isSeen;

}