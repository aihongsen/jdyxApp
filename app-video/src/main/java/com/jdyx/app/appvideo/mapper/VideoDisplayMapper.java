package com.jdyx.app.appvideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDisplayMapper extends BaseMapper<VideoDisplay> {

    List<VideoDisplayVo> getAllVideoDisplayVo();
}