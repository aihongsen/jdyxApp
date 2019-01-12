package com.jdyx.app.appvideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDisplayMapper extends BaseMapper<VideoDisplay> {

    List<VideoDisplayVo> getAllVideoDisplayVo(@Param("jobId") Integer jobId, @Param("releaseType") Integer releaseType, @Param("pageNow") Integer pageNow, @Param("pageSize") Integer pageSize);

    List<VideoDisplay> getAllVideoDisplayPageById(@Param("userId") Integer userId, @Param("pageNow") Integer pageNow, @Param("pageSize") Integer pageSize);

}