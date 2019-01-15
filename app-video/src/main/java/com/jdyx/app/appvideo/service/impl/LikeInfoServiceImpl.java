package com.jdyx.app.appvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdyx.app.appvideo.mapper.LikeInfoMapper;
import com.jdyx.app.bean.LikeAndAttentionVo;
import com.jdyx.app.bean.LikeInfo;
import com.jdyx.app.service.LikeInfoService;
import com.jdyx.app.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeInfoServiceImpl implements LikeInfoService {

    @Autowired
    LikeInfoMapper likeInfoMapper;

    @Override
    public int saveLikeInfo(LikeInfo likeInfo) {

        return likeInfoMapper.insert(likeInfo);

    }

    @Override
    public void deleteLikeInfo(LikeInfo likeInfo) {
        likeInfoMapper.delete(new QueryWrapper<LikeInfo>().eq("video_id",likeInfo.getVideoId()).eq("followed_id",likeInfo.getFollowedId()));
    }

    @Override
    public LikeInfo getLikeInfo(LikeInfo likeInfo) {
        return likeInfoMapper.selectOne(new QueryWrapper<LikeInfo>().eq("video_id",likeInfo.getVideoId()).eq("followed_id",likeInfo.getFollowedId()));
    }

    @Override
    public LikeAndAttentionVo getLikeInfoAndAttentionInfo(Integer videoId, Integer userId) {
        return likeInfoMapper.getLikeInfoAndAttentionInfo(videoId,userId);
    }
}
