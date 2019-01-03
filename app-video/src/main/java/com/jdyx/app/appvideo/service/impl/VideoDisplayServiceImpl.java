package com.jdyx.app.appvideo.service.impl;

import com.jdyx.app.appvideo.mapper.VideoDisplayMapper;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.service.VideoDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDisplayServiceImpl implements VideoDisplayService {
    @Autowired
    VideoDisplayMapper videoDisplayMapper;

    @Override
    public List<VideoDisplay> getAllVideoDisplay(){
        return videoDisplayMapper.selectList(null);
    }

    @Override
    public void saveVideoDisplay(VideoDisplay videoDisplay) {
        videoDisplayMapper.insert(videoDisplay);
    }


}