package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.bean.Post;
import com.jdyx.app.service.PostService;
import com.jdyx.app.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/getAllPost")
    @ResponseBody
    public Map<String,String> getAllPost(){
        try {
            List<Post> allPost = postService.getAllPost();
            String json = JSON.toJSONString(allPost);
            log.info("StringJson : {}",json);
            return ResultUtil.successMap(json);
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }

    }

    @RequestMapping("/savePost")
    @ResponseBody
    public Map<String,String> savePost( Post post){
        try {
            postService.savePost(post);
            log.info("添加岗位的id ：{}",post.getId());
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }

    @RequestMapping("/deletePost")
    @ResponseBody
    public Map<String,String>  deletePost(Post post){
        try {
            postService.savePost(post);
            log.info("删除岗位的id ：{}", post.getId());
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }
}
