package com.jdyx.app.appvideo.service.impl;

import com.jdyx.app.appvideo.mapper.PostMapper;
import com.jdyx.app.bean.Post;
import com.jdyx.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper  postMapper;
    @Override
    public List<Post> getAllPost() {
        return postMapper.selectList(null);
    }

    @Override
    public int savePost(Post post) {
        return postMapper.insert(post);
    }
}
