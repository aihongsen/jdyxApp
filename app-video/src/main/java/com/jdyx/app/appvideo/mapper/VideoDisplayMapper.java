package com.jdyx.app.appvideo.mapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.VideoDisplay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDisplayMapper extends BaseMapper<VideoDisplay> {
    @Override
    List<VideoDisplay> selectList(Wrapper<VideoDisplay> wrapper);
}