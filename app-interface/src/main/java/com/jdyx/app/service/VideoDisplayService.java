package com.jdyx.app.service;

import com.jdyx.app.bean.VideoDisplay;

import java.util.List;

public interface VideoDisplayService {
    /**
     * 获取所有视频
     * @return
     */
    List<VideoDisplay> getAllVideoDisplay();

    /**
     * 保存视频
     */
    void saveVideoDisplay(VideoDisplay videoDisplay);



}
