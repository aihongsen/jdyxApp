package com.jdyx.app.appvideo.controller;

import com.jdyx.app.bean.LikeInfo;
import com.jdyx.app.service.LikeInfoService;
import com.jdyx.app.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RequestMapping("/like")
@Controller
public class LikeInfoController  {
    @Autowired
    JedisPool jedisPool;

    @Autowired
    LikeInfoService likeInfoService;

    /**
     * 视频点赞
     * @param likeInfo
     * @return
     */
    @RequestMapping(value = "/likeVideo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="视频点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "userId", value = "短视频所属人id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "followedId", value = "点赞人id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "follName", value = "点赞人姓名", required = true, dataType = "String")
    })
    public Object likeVideo(LikeInfo likeInfo){
        if (likeInfo.getVideoId()==null){
            return ResultUtil.exceptionMap("2019","视频id无效");
        }
        if (likeInfo.getUserId()==null){
            return ResultUtil.exceptionMap("2019","短视频所属人id无效");
        }
        if (likeInfo.getFollowedId()==null){
            return ResultUtil.exceptionMap("2019","点赞人id无效");
        }
        if (likeInfo.getFollName()==null){
            return ResultUtil.exceptionMap("2019","点赞人姓名id无效");
        }
        Jedis jedis = jedisPool.getResource();
        try {
            //存储关注信息到数据库
            likeInfoService.saveLikeInfo(likeInfo);
            //redis点赞分数加1
            jedis.incr("vdieo:"+likeInfo.getVideoId());

            return ResultUtil.successMap("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.exceptionMap("2020","存储数据失败");
        } finally {
            jedis.close();
        }
    }
    /**
     * 视频点赞
     * @param likeInfo
     * @return
     */
    @RequestMapping(value = "/likeVideo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="视频点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "followedId", value = "取消点赞人id", required = true, dataType = "String")
    })
    public Object cancellikeVideo(LikeInfo likeInfo){
        if (likeInfo.getVideoId()==null){
            return ResultUtil.exceptionMap("2019","视频id无效");
        }
        if (likeInfo.getFollowedId()==null){
            return ResultUtil.exceptionMap("2019","取消点赞人id无效");
        }
        Jedis jedis = jedisPool.getResource();
        try {
            //存储关注信息到数据库
            likeInfoService.deleteLikeInfo(likeInfo);
            //redis点赞分数加1
            jedis.decr("vdieo:"+likeInfo.getVideoId());
            return ResultUtil.successMap("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorMap();
        } finally {
            jedis.close();
        }
    }
}
