package com.jdyx.app.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:like_info表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-02
 */
public class LikeInfo implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 编号
     * @return id 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 编号
     * @param id 编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 短视频id
     * @return video_id 短视频id
     */
    public Integer getVideoId() {
        return videoId;
    }

    /**
     * 短视频id
     * @param videoId 短视频id
     */
    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    /**
     * 短视频所属人id
     * @return user_id 短视频所属人id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 短视频所属人id
     * @param userId 短视频所属人id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 点赞人id
     * @return followed_id 点赞人id
     */
    public Integer getFollowedId() {
        return followedId;
    }

    /**
     * 点赞人id
     * @param followedId 点赞人id
     */
    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }

    /**
     * 点赞人姓名
     * @return foll_name 点赞人姓名
     */
    public String getFollName() {
        return follName;
    }

    /**
     * 点赞人姓名
     * @param follName 点赞人姓名
     */
    public void setFollName(String follName) {
        this.follName = follName == null ? null : follName.trim();
    }

    /**
     * 关注日期
     * @return followed_date 关注日期
     */
    public Date getFollowedDate() {
        return followedDate;
    }

    /**
     * 关注日期
     * @param followedDate 关注日期
     */
    public void setFollowedDate(Date followedDate) {
        this.followedDate = followedDate;
    }
}