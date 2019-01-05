package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.appvideo.constant.VideoConst;
import com.jdyx.app.bean.VideoDisplay;
import com.jdyx.app.bean.VideoDisplayVo;
import com.jdyx.app.service.VideoDisplayService;
import com.jdyx.app.util.DistanceUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/video")
@Controller
public class VideoDisplayController {

    @Autowired
    VideoDisplayService videoDisplayService;

    @RequestMapping("/getAllVideoDisplay")
    @ResponseBody
    public String getAllVideoDisplay(BigDecimal longitude, BigDecimal latitude){
        List<VideoDisplayVo> allVideoDisplay = videoDisplayService.getAllVideoDisplayVo();
        for (VideoDisplayVo videoDisplayVo : allVideoDisplay) {
            String distance = DistanceUtil.algorithm(videoDisplayVo.getLongitude().doubleValue(), videoDisplayVo.getLatitude().doubleValue(), longitude.doubleValue(), latitude.doubleValue());
            videoDisplayVo.setDistance(distance);
        }
        String json = JSON.toJSONString(allVideoDisplay);
        log.info("StringJson : {}",json);
        return  json;
    }

    @RequestMapping("/getAllVideoDisplayById")
    @ResponseBody
    public String getAllVideoDisplayById(Integer userId){
        List<VideoDisplay> allVideoDisplay = videoDisplayService.getAllVideoDisplayById(userId);
        String json = JSON.toJSONString(allVideoDisplay);
        log.info("获取该用户所有视频 ：{}",json);
        return  json;
    }

    /**
     * 保存视频数据
     * @param videoDisplay
     * @param userId
     * @return
     */
    @RequestMapping("/saveVideoDisplay")
    @ResponseBody
    public String saveVideoDisplay(VideoDisplay videoDisplay,String userId){

        videoDisplay.setVideoDate(new Date());
        videoDisplay.setUserId(Integer.valueOf(userId));
        //根据地址获取视频文件名
        String bucketKey = videoDisplay.getVideoAddress().replace(VideoConst.QINIU_ADRESS, "");
        try {
            //对视频增加水印,生成有水印的视频地址
            String videoWatermarkAddress = produceVoideoWatermark(userId, videoDisplay.getReleaseType(), bucketKey);
            videoDisplay.setVideoWatermarkAddress(videoWatermarkAddress);
            log.info("视频水印地址：{}",videoWatermarkAddress);
        } catch (IOException e) {
            log.error("对视频增加水印失败 ：{}",e);
        }
        videoDisplayService.saveVideoDisplay(videoDisplay);
        return "OK";
    }

    /**
     * 对视频增加水印
     * @param userId
     * @param releaseType
     * @param bucketKey 七牛云空间文件名
     * @return  新视频地址
     * @throws IOException
     */
    public String produceVoideoWatermark( String userId,String releaseType,String bucketKey) throws IOException {
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
            }
            return "";
        }
    }


}
