package com.jdyx.app.appvideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.VideoDisplay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDisplayMapper extends BaseMapper<VideoDisplay> {

    List<VideoDisplay> getAllVideoDisplayById(@Param("userId") Integer userId);
}