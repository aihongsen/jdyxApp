package com.jdyx.app.appvideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.LikeAndAttentionVo;
import com.jdyx.app.bean.LikeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeInfoMapper extends BaseMapper<LikeInfo>{

    LikeAndAttentionVo getLikeInfoAndAttentionInfo(@Param("videoId") Integer videoId, @Param("followedId") Integer followedId);
}
