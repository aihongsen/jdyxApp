package com.jdyx.app.appvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdyx.app.appvideo.mapper.VideoDisplayMapper;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;
import com.jdyx.app.service.VideoDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDisplayServiceImpl implements VideoDisplayService {
    @Autowired
    VideoDisplayMapper videoDisplayMapper;

    @Override
    public List<VideoDisplayVo> getAllVideoDisplayVo(Integer jobId, Integer pageNow,Integer pageSize){
        return videoDisplayMapper.getAllVideoDisplayVo(jobId,pageNow,pageSize);
    }

    @Override
    public void saveVideoDisplay(VideoDisplay videoDisplay) {
        videoDisplayMapper.insert(videoDisplay);
    }

    @Override
    public List<VideoDisplay> getAllVideoDisplayById(Integer userId) {
        QueryWrapper<VideoDisplay> queryWrapper = new QueryWrapper<VideoDisplay>().eq("user_id",userId);
        return videoDisplayMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteVideoDisplay(Integer id) {
        videoDisplayMapper.delete(new QueryWrapper<VideoDisplay>().eq("id",id));
    }

    @Override
    public void watchVideo(Integer videoId) {

        VideoDisplay videoDisplay = videoDisplayMapper.selectById(videoId);
        videoDisplay.setVideoViews(videoDisplay.getVideoViews()+1);
        videoDisplayMapper.updateById(videoDisplay);
    }

    @Override
    public VideoDisplay likeVideo(Integer videoId) {
        VideoDisplay videoDisplay = videoDisplayMapper.selectById(videoId);
        videoDisplay.setVideoLikes(videoDisplay.getVideoLikes()+1);
        int i = videoDisplayMapper.updateById(videoDisplay);
        return videoDisplay;
    }

    @Override
    public VideoDisplay cancelLikeVideo(Integer videoId) {
        VideoDisplay videoDisplay = videoDisplayMapper.selectById(videoId);
        videoDisplay.setVideoLikes(videoDisplay.getVideoLikes()-1);
        videoDisplayMapper.updateById(videoDisplay);
        return videoDisplay;
    }

    @Override
    public VideoDisplay getVideoDisplayByVideoId(Integer videoId) {
        return videoDisplayMapper.selectById(videoId);
    }

    @Override
    public int getVideoDisplayTotalByJobId(Integer jobId) {
        return videoDisplayMapper.selectCount(new QueryWrapper<VideoDisplay>().eq("job_id",jobId));
    }


}
