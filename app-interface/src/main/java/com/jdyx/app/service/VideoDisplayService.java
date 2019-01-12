package com.jdyx.app.service;

import com.jdyx.app.bean.Page;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;

import java.util.List;

public interface VideoDisplayService {
    /**
     * 获取所有视频
     * @return
     */
    List<VideoDisplayVo> getAllVideoDisplayVo(Integer jobId,Integer releaseType,Integer pageNow,Integer pageSize);

    /**
     * 保存视频
     */
    void saveVideoDisplay(VideoDisplay videoDisplay);

    /**
     * 获取该用户所有视频
     * @return
     */
    List<VideoDisplay> getAllVideoDisplayById(Integer userId);

    void deleteVideoDisplay(Integer videoId);

    void watchVideo(Integer videoId);

    VideoDisplay likeVideo(Integer videoId);

    VideoDisplay cancelLikeVideo(Integer videoId);

    VideoDisplay getVideoDisplayByVideoId(Integer videoId);

    int getVideoDisplayTotalByJobId(Integer jobId,Integer releaseType);

    int getVideoDisplayTotal(Integer releaseType);

    List<VideoDisplay> getAllVideoDisplayPageById(Integer userId, Integer pageNow, Integer pageSize);
}
