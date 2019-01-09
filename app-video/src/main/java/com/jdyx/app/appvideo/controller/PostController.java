package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.bean.Post;
import com.jdyx.app.service.PostService;
import com.jdyx.app.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping(value = "/getAllPost",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取所有岗位")
    public Object getAllPost(){
        try {
            List<Post> allPost = postService.getAllPost();
            Object json = JSON.toJSON(allPost);
            log.info("StringJson : {}",json);
            return ResultUtil.successMap(json);
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }

    }

    @RequestMapping(value = "/savePost",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("保存岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "岗位名称", required = true, dataType = "String"),
    })
    public Object savePost(Post post){
        if (post.getName()==null){
            return ResultUtil.exceptionMap("2019","岗位名称无效");
        }
        try {
            postService.savePost(post);
            log.info("添加岗位的id ：{}",post.getId());
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }

    @RequestMapping(value = "/deletePost",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("删除岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "岗位id", required = true, dataType = "String"),
    })
    public Object  deletePost(Integer id){
        if (id==null){
            return ResultUtil.exceptionMap("2019","岗位id无效");
        }
        try {
            postService.deletePost(id);
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }
}
