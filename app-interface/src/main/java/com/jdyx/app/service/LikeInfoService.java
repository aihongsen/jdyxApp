package com.jdyx.app.service;

import com.jdyx.app.bean.LikeAndAttentionVo;
import com.jdyx.app.bean.LikeInfo;

public interface LikeInfoService  {

    int saveLikeInfo(LikeInfo likeInfo);

    void deleteLikeInfo(LikeInfo likeInfo);

    LikeInfo getLikeInfo(LikeInfo likeInfo);

    LikeAndAttentionVo getLikeInfoAndAttentionInfo(Integer videoId, Integer followedId);
}
