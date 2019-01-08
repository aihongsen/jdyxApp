package com.jdyx.app.service;

import com.jdyx.app.bean.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPost();

    int savePost(Post post);

    void deletePost(Integer id);
}
