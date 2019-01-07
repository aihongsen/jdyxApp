package com.jdyx.app.service;

import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;

import java.util.List;

public interface VideoDisplayService {
    /**
     * 获取所有视频
     * @return
     */
    List<VideoDisplayVo> getAllVideoDisplayVo();

    /**
     * 保存视频
     */
    void saveVideoDisplay(VideoDisplay videoDisplay);

    /**
     * 获取该用户所有视频
     * @return
     */
    List<VideoDisplay> getAllVideoDisplayById(Integer userId);
}
