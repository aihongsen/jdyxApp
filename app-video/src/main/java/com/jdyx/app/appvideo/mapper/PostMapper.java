package com.jdyx.app.appvideo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PostMapper  extends BaseMapper<Post>{
    @Override
    List<Post> selectList(Wrapper<Post> wrapper);
}