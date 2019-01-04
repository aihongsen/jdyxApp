package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.bean.Post;
import com.jdyx.app.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    PostService postService;

    @RequestMapping("/getAllPost")
    @ResponseBody
    public List<Post> getAllPost(){
        List<Post> allPost = postService.getAllPost();
        Object o = JSON.toJSON(allPost);
        log.info("objectJSON : {}",o);
        String json = String.valueOf(JSON.toJSON(allPost));
        log.info("StringJson : {}",json);
        return allPost;
    }

    @RequestMapping("/savePost")
    @ResponseBody
    public String savePost( Post post){
        postService.savePost(post);
        log.info("添加岗位的id ：{}",post.getId());
        return "OK";
    }

}
