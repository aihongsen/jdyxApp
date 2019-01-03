package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:message表的实体类
 */
@Data
public class Message implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID 关联用户ID
     */
    private Integer userId;

    /**
     * 消息类型 关联消息ID
     */
    private Integer msType;

    /**
     * 消息发布人 京典小管家(ID)
     */
    private Integer announcer;

    /**
     * 消息发布日期
     */
    private Date sendDate;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已看
     */
    private String isSeen;
}