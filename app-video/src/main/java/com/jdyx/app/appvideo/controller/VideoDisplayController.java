package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.appvideo.constant.VideoConst;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;
import com.jdyx.app.service.VideoDisplayService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
            @ApiImplicitParam(paramType="query", name = "latitude", value = "经度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "longitude", value = "纬度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "jobId", value = "岗位id", required = false, dataType = "Integer")
    })
    public Map<String,String> getAllVideoDisplay(BigDecimal longitude, BigDecimal latitude,Integer jobId){
        List<VideoDisplayVo> allVideoDisplay = videoDisplayService.getAllVideoDisplayVo(jobId);
        if(longitude == null || latitude == null ){
            log.info("无定位信息");
            return ResultUtil.successMap(JSON.toJSONString(allVideoDisplay));
        }
        for (VideoDisplayVo videoDisplayVo : allVideoDisplay) {
            String distance = DistanceUtil.algorithm(videoDisplayVo.getLongitude().doubleValue(), videoDisplayVo.getLatitude().doubleValue(), longitude.doubleValue(), latitude.doubleValue());
            videoDisplayVo.setDistance(distance);
        }
        String json = JSON.toJSONString(allVideoDisplay);
        log.info("StringJson : {}",json);
        return ResultUtil.successMap(JSON.toJSONString(allVideoDisplay));
    }

    @RequestMapping(value = "/getAllVideoDisplayById",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="查看本人视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Map<String,String> getAllVideoDisplayById(Integer userId){
        try {
            List<VideoDisplay> allVideoDisplay = videoDisplayService.getAllVideoDisplayById(userId);
            String json = JSON.toJSONString(allVideoDisplay);
            log.info("获取该用户所有视频 ：{}",json);
            return ResultUtil.successMap(JSON.toJSONString(allVideoDisplay));
        }catch (Exception e){
            log.error("",e);
            return ResultUtil.errorMap();
        }
    }

    /**
     * 保存视频数据
     * @param videoDisplay
     * @return
     */
    @RequestMapping(value = "/saveVideoDisplay",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="查看本人视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoDisplay", value = "自定义视频类", required = true, dataType = "VideoDisplay")
    })
    public Map<String,String> saveVideoDisplay(@RequestBody VideoDisplay videoDisplay){
        if(videoDisplay ==null){
            return ResultUtil.exceptionMap("403","参数错误");
        }
        videoDisplay.setVideoDate(new Date());
        videoDisplay.setUserId(videoDisplay.getUserId());
        //根据地址获取视频文件名
        String bucketKey = videoDisplay.getVideoAddress().replace(VideoConst.QINIU_ADRESS, "");
        try {
            //对视频增加水印,生成有水印的视频地址
            String videoWatermarkAddress = produceVoideoWatermark(videoDisplay.getUserId(), videoDisplay.getReleaseType(), bucketKey);
            videoDisplay.setVideoWatermarkAddress(videoWatermarkAddress);
            log.info("视频水印地址：{}",videoWatermarkAddress);
            videoDisplayService.saveVideoDisplay(videoDisplay);
            return ResultUtil.successMap("");
        } catch (IOException e) {
            log.error("对视频增加水印失败 ：{}",e);
            return ResultUtil.errorMap();
        }
    }

    /**
     * 逻辑删除视频
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/deleteVideoDisplay",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> deleteVideoDisplay(Integer videoId){
        try {
            videoDisplayService.deleteVideoDisplay(videoId);
            return ResultUtil.successMap("");
        }catch (Exception e){
            return ResultUtil.errorMap();
        }
    }

    /**
     * 对视频增加水印
     * @param userId
     * @param releaseType
     * @param bucketKey 七牛云空间文件名
     * @return  新视频地址
     * @throws IOException
     */
    public String produceVoideoWatermark(Integer userId,String releaseType,String bucketKey) throws IOException {
        //设置账号的AK,SK
        Auth auth = Auth.create(VideoConst.ACCESS_KEY, VideoConst.SECRET_KEY);

        //新建一个OperationManager对象
        Configuration cfg = new Configuration();
        OperationManager operater = new OperationManager(auth,cfg);

        String bucket = VideoConst.BUCKET_NAME;
        //需要添加水印的图片UrlSafeBase64,可以参考http://developer.qiniu.com/code/v6/api/dora-api/av/video-watermark.html
        String pictureurl = UrlSafeBase64.encodeToString(VideoConst.PICTURE_URL);
        //设置转码操作参数
//        String fops = "avthumb/mp4/s/640x360/vb/1.25m/wmImage/" + pictureurl;
        String fops = "avwatermarks/mp4/wmImage/" + pictureurl
                +"/wmIgnoreLoop/0/Constant/1/wmGravity/NorthWest";
        //设置转码的队列
        String pipeline = VideoConst.PIPELINE;

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
            return VideoConst.QINIU_ADRESS + newVideoName;
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

    /**
     * 视频点赞
     * @param userId
     * @param response
     * @return
     */
    @RequestMapping(value = "/likeVideo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> likeVideo(String userId){

        return ResultUtil.successMap(userId);
    }

}
