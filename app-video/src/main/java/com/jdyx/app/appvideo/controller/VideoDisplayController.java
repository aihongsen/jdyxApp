package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.bean.LikeInfo;
import com.jdyx.app.bean.Page;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;
import com.jdyx.app.service.VideoDisplayService;
import com.jdyx.app.util.Const;
import com.jdyx.app.util.DistanceUtil;
import com.jdyx.app.util.ResultUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequestMapping("/video")
@Controller
public class VideoDisplayController {

    @Autowired
    VideoDisplayService videoDisplayService;

    @Autowired
    JedisPool jedisPool;
    /**
     *
     * @param longitude
     * @param latitude
     * @return
     */
    @RequestMapping(value = "/getAllVideoDisplay",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="查看所有视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNow", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页显示记录的条数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "latitude", value = "经度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "longitude", value = "纬度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "jobId", value = "岗位id", required = false, dataType = "Integer")
    })
    public Object getAllVideoDisplay(Page page, BigDecimal longitude, BigDecimal latitude, Integer jobId){
        List<VideoDisplayVo> allVideoDisplay = videoDisplayService.getAllVideoDisplayVo(jobId,page.getPageNow()*page.getPageSize(),page.getPageSize());
        if(longitude == null || latitude == null ){
            log.info("无定位信息");
        }else {
            for (VideoDisplayVo videoDisplayVo : allVideoDisplay) {
                String distance = DistanceUtil.algorithm(videoDisplayVo.getLongitude().doubleValue(), videoDisplayVo.getLatitude().doubleValue(), longitude.doubleValue(), latitude.doubleValue());
                videoDisplayVo.setDistance(distance);
            }
        }
        page.setVideoDisplayVo(allVideoDisplay);
        return ResultUtil.successMap(JSON.toJSON(page));
    }

    @RequestMapping(value = "/getAllVideoDisplayById",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="查看本人视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Object getAllVideoDisplayById(Integer userId){
        if (userId == null){
            return ResultUtil.exceptionMap("2019","无效的参数数据");
        }
        try {
            List<VideoDisplay> allVideoDisplay = videoDisplayService.getAllVideoDisplayById(userId);
            Object json = JSON.toJSON(allVideoDisplay);
            log.info("获取该用户所有视频 ：{}",json);
            return ResultUtil.successMap(JSON.toJSON(allVideoDisplay));
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }

    /**
     * 保存视频数据
     * @param videoDisplay
     * {
    "jobId": 22,
    "musicAddress": "音乐地址",
    "releaseType": "0",
    "userId": 4,
    "videoAddress": "http://v.jdyxqq.com/Fg4_BC95PkLiPLDgfnCBmHhU5P_o",
    "videoDescription": "视频描述",
    "videoPicture": "视频封面图片",
    "videoTitle": "视频标题"
    }
     * @return
     */
    @RequestMapping(value = "/saveVideoDisplay",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="保存视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "jobId", value = "岗位id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "musicAddress", value = "音乐地址", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "releaseType", value = "短视频类型", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "videoAddress", value = "上传视频地址", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "videoDescription", value = "视频描述", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "videoPicture", value = "视频封面图片", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "videoTitle", value = "视频标题", required = true, dataType = "String")
    })
    public Object saveVideoDisplay(VideoDisplay videoDisplay, HttpServletResponse response){
        if(videoDisplay ==null){
            return ResultUtil.exceptionMap("2019","无效的参数数据");
        }
        videoDisplay.setVideoDate(new Date());
        //根据地址获取视频文件名
        if(!videoDisplay.getVideoAddress().contains(Const.QINIU_ADRESS)){
            return ResultUtil.exceptionMap("2019","视频地址错误");
        }
        String bucketKey = videoDisplay.getVideoAddress().replace(Const.QINIU_ADRESS, "");
        if(StringUtils.isEmpty(bucketKey)){
            return ResultUtil.exceptionMap("2019","视频地址错误");
        }
        try {
            //对视频增加水印,生成有水印的视频地址
            String videoWatermarkAddress = produceVoideoWatermark(videoDisplay.getUserId(), videoDisplay.getReleaseType(), bucketKey);
            if(StringUtils.isEmpty(videoWatermarkAddress)){
                return ResultUtil.exceptionMap("2021","视频增加水印失败");
            }
            videoDisplay.setVideoWatermarkAddress(videoWatermarkAddress);
            log.info("视频水印地址：{}",videoWatermarkAddress);
        } catch (IOException e) {
            log.error("对视频增加水印失败 ：{}",e);
            return ResultUtil.errorMap();
        }
        try {
            videoDisplayService.saveVideoDisplay(videoDisplay);
            Jedis jedis = jedisPool.getResource();
            jedis.zadd(Const.LIKE_VIDEO+videoDisplay.getId(),0,"");
            jedis.zadd(Const.WATCH_VIDEO+videoDisplay.getId(),0,"");
            return ResultUtil.successMap("");
        }catch (Exception e){
            return ResultUtil.exceptionMap("2020","存储数据失败");
        }

    }

    /**
     * 逻辑删除视频
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/deleteVideoDisplay",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="保存视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String")
    })
    public Object deleteVideoDisplay(Integer videoId){
        if(videoId ==null){
            return ResultUtil.exceptionMap("2019","无效的参数数据");
        }
        try {
            videoDisplayService.deleteVideoDisplay(videoId);
            return ResultUtil.successMap("");
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }

    /**
     * 对视频增加水印
     * @param userId
     * @param releaseType
     * @param bucketKey 七牛云空间文件名
     * @return 新视频地址
     * @throws IOException
     */
    public String produceVoideoWatermark(Integer userId,String releaseType,String bucketKey) throws IOException {
        //设置账号的AK,SK
        Auth auth = Auth.create(Const.ACCESS_KEY, Const.SECRET_KEY);

        //新建一个OperationManager对象
        Configuration cfg = new Configuration();
        OperationManager operater = new OperationManager(auth,cfg);

        String bucket = Const.BUCKET_NAME;
        //需要添加水印的图片UrlSafeBase64,可以参考http://developer.qiniu.com/code/v6/api/dora-api/av/video-watermark.html
        String pictureurl = UrlSafeBase64.encodeToString(Const.PICTURE_URL);
        //设置转码操作参数
        String fops = "avwatermarks/mp4/wmImage/" + pictureurl
                +"/wmIgnoreLoop/0/Constant/1/wmGravity/NorthWest";
        //设置转码的队列
        String pipeline = Const.PIPELINE;

        //创建日期格式化对象   因为DateFormat类为抽象类 所以不能new
        DateFormat bf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();//创建时间
        String format = bf.format(date);//格式化 bf.format(date);
        String newVideoName = (UUID.randomUUID() + "").substring(0,8).replace("-","") + format + userId + releaseType;
        String urlbase64 = UrlSafeBase64.encodeToString(pipeline +":" + newVideoName);

        //可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
        String pfops = fops + "|saveas/" + urlbase64;
        //设置pipeline参数
        StringMap params = new StringMap().putWhen("force", 1, true).putNotEmpty("pipeline", pipeline);
        try {
            //设置要转码的空间bucket和bucketKey，并且这个bucketKey在你空间中存在
            String persistid = operater.pfop(bucket, bucketKey, pfops, params);
            //http://v.jdyxqq.com/57eda8b82019010410
            return Const.QINIU_ADRESS + newVideoName;
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            log.info("请求失败时简单状态信息 :{}",r.toString());
            try {
                log.info("响应的文本信息 :{}",r.bodyString());
            } catch (QiniuException e1) {
                //ignore
                log.info("",e1);
            }
            return "";
        }
    }

    @RequestMapping(value = "/watchVideo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="观看视频，增加视频播放次数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String")
    })
    public Object watchVideo(Integer videoId){
        if (videoId==null){
            return ResultUtil.exceptionMap("2019","视频id无效");
        }
        Jedis jedis = jedisPool.getResource();
        try {
            //redis播放次数加1
            jedis.incr(Const.WATCH_VIDEO+videoId);
            return ResultUtil.successMap("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.exceptionMap("2020","存储数据失败");
        } finally {
            jedis.close();
        }
    }


}
