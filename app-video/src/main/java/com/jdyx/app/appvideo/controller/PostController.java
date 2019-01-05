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
    public String getAllPost(){
        List<Post> allPost = postService.getAllPost();
        String json = JSON.toJSONString(allPost);
        log.info("StringJson : {}",json);
        return json;
    }

    @RequestMapping("/savePost")
    @ResponseBody
    public String savePost( Post post){
        postService.savePost(post);
        log.info("添加岗位的id ：{}",post.getId());
        return "OK";
    }
    @RequestMapping("/deletePost")
    @ResponseBody
    public String deletePost( Post post){
        postService.savePost(post);
        log.info("删除岗位的id ：{}",post.getId());
        return "OK";
    }
}
