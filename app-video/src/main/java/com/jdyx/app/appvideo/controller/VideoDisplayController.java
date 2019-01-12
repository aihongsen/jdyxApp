package com.jdyx.app.appvideo.controller;

import com.alibaba.fastjson.JSON;
import com.jdyx.app.bean.*;
import com.jdyx.app.service.LikeInfoService;
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
    LikeInfoService likeInfoService;

    @Autowired
    VideoDisplayService videoDisplayService;

    /**
     *
     * @param longitude
     * @param latitude
     * @return
     */
    @RequestMapping(value = "/getAllVideoDisplay",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查看所有视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNow", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页显示记录的条数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "latitude", value = "经度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "longitude", value = "纬度", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "jobId", value = "岗位id", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "releaseType", value = "短视频类型", required = true, dataType = "String")
    })
    public Object getAllVideoDisplay(Page page, BigDecimal longitude, BigDecimal latitude, Integer jobId, Integer releaseType){
        if (releaseType == null){
            return ResultUtil.exceptionMap(2019,"releaseType无效");
        }
        if(jobId != null && -1 == jobId ){
            jobId = null;
        }
        List<VideoDisplayVo> allVideoDisplay = videoDisplayService.getAllVideoDisplayVo(jobId,releaseType,page.getPageNow()*page.getPageSize(),page.getPageSize());
        if(longitude == null || latitude == null){
            log.info("无定位信息");
        }else {
            for (VideoDisplayVo videoDisplayVo : allVideoDisplay) {
                String distance = DistanceUtil.algorithm(videoDisplayVo.getLongitude().doubleValue(), videoDisplayVo.getLatitude().doubleValue(), longitude.doubleValue(), latitude.doubleValue());
                videoDisplayVo.setDistance(distance);
            }
        }
        //根据jobid获取总记录数
        int totalRow = 0;
        if(jobId == null ){
            totalRow = videoDisplayService.getVideoDisplayTotal(releaseType);
        }else {
            totalRow = videoDisplayService.getVideoDisplayTotalByJobId(jobId,releaseType);
        }
        //设置当前页数据
        page.setVideoDisplayVo(allVideoDisplay);
        //设置当前页记录数
        page.setPageSize(allVideoDisplay.size());
        //设置总记录条数和总页数
        page.setTotalRow(totalRow);
        page.setTotalPage(page.getTotalRow());
        return ResultUtil.successMap(JSON.toJSON(page));
    }

    @RequestMapping(value = "/getAllVideoDisplayById",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查看本人视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNow", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页显示记录的条数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Object getAllVideoDisplayByUserId(Page page,Integer userId){
        if (userId == null || userId == 0){
            return ResultUtil.exceptionMap(2019,"userId无效");
        }
        try {
            //查找当前页数据
            List<VideoDisplay> allVideoDisplay = videoDisplayService.getAllVideoDisplayPageById(userId,page.getPageNow(),page.getPageSize());
            //查看该用户总记录数
            List<VideoDisplay> allVideoDisplayById = videoDisplayService.getAllVideoDisplayById(userId);
            //设置当前页数据
            page.setVideoDisplayVo(allVideoDisplay);
            //设置当前页记录数
            page.setPageSize(allVideoDisplay.size());
            //设置总记录条数和总页数
            page.setTotalRow(allVideoDisplayById.size());
            page.setTotalPage(page.getTotalRow());
            return ResultUtil.successMap(page);
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
    public Object saveVideoDisplay(VideoDisplay videoDisplay){
        if(videoDisplay.getUserId() == null || videoDisplay.getUserId()==0){
            return ResultUtil.exceptionMap(2019,"用户id无效");
        }
        if(videoDisplay.getJobId() == null || videoDisplay.getJobId()==0){
            return ResultUtil.exceptionMap(2019,"岗位id无效");
        }
        if(videoDisplay.getReleaseType() == null || videoDisplay.getReleaseType()<0 || videoDisplay.getReleaseType()>1){
            return ResultUtil.exceptionMap(2019,"短视频类型无效");
        }
        if(StringUtils.isEmpty(videoDisplay.getVideoAddress()) || !videoDisplay.getVideoAddress().contains(Const.QINIU_ADRESS)){
            return ResultUtil.exceptionMap(2019,"视频地址无效");
        }
        if(StringUtils.isEmpty(videoDisplay.getVideoDescription())){
            return ResultUtil.exceptionMap(2019,"视频描述无效");
        }
        if(StringUtils.isEmpty(videoDisplay.getVideoPicture())){
            return ResultUtil.exceptionMap(2019,"视频封面图片无效");
        }
        if(StringUtils.isEmpty(videoDisplay.getVideoAddress())){
            return ResultUtil.exceptionMap(2019,"视频标题无效");
        }
        videoDisplay.setVideoDate(new Date());
        //根据地址获取视频文件名
        String bucketKey = videoDisplay.getVideoAddress().replace(Const.QINIU_ADRESS, "");
        try {
            //对视频增加水印,生成有水印的视频地址
            String videoWatermarkAddress = produceVoideoWatermark(videoDisplay.getUserId(), videoDisplay.getReleaseType(), bucketKey);
            if(StringUtils.isEmpty(videoWatermarkAddress)){
                return ResultUtil.exceptionMap(2021,"视频增加水印失败");
            }
            videoDisplay.setVideoWatermarkAddress(videoWatermarkAddress);
            log.info("视频水印地址：{}",videoWatermarkAddress);
        } catch (IOException e) {
            log.error("对视频增加水印失败 ：{}",e);
            return ResultUtil.errorMap();
        }
        try {
            videoDisplayService.saveVideoDisplay(videoDisplay);
            return ResultUtil.successMap("");
        }catch (Exception e){
            return ResultUtil.exceptionMap(2020,"存储数据失败");
        }
    }
    /**
     * 根据视频id查看个人视频
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/getVideoDisplayByVideoId",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="保存视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String")
    })
    public Object getVideoDisplayByVideoId(Integer videoId){
        if(videoId ==null || videoId==0 ){
            return ResultUtil.exceptionMap(2019,"视频id无效");
        }
        try {
            VideoDisplay videoDisplay = videoDisplayService.getVideoDisplayByVideoId(videoId);
            return ResultUtil.successMap(videoDisplay);
        }catch (Exception e){
            log.error("",e);
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
    @ApiOperation(value="保存视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String")
    })
    public Object deleteVideoDisplay(Integer videoId){
        if(videoId ==null || videoId==0 ){
            return ResultUtil.exceptionMap(2019,"无效的参数数据");
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
    public String produceVoideoWatermark(Integer userId,Integer releaseType,String bucketKey) throws IOException {
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
        if (videoId==null || videoId==0){
            return ResultUtil.exceptionMap(2019,"视频id无效");
        }
        try {
            //数据库修改播放次数
            videoDisplayService.watchVideo(videoId);
            return ResultUtil.successMap("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.exceptionMap(2020,"存储数据失败");
        }
    }
    /**
     * 视频点赞
     * @param likeInfo
     * @return
     */
    @RequestMapping(value = "/getLikeInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="视频点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "followedId", value = "点赞人id", required = true, dataType = "String"),
    })
    public Object getLikeInfo(LikeInfo likeInfo){
        if (likeInfo.getVideoId()==null || likeInfo.getVideoId()==0){
            return ResultUtil.exceptionMap(2019,"视频id无效");
        }
        if (likeInfo.getFollowedId()==null || likeInfo.getFollowedId()==0 ){
            return ResultUtil.exceptionMap(2019,"点赞人id无效");
        }
        LikeInfo selectOne = likeInfoService.getLikeInfo(likeInfo);
        return ResultUtil.successMap("");
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
            @ApiImplicitParam(paramType="query",name = "userId", value = "短视频所属人id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "followedId", value = "点赞人id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "follName", value = "点赞人姓名", required = true, dataType = "String")
    })
    public Object likeVideo(LikeInfo likeInfo){
        if (likeInfo.getVideoId()==null || likeInfo.getVideoId()==0){
            return ResultUtil.exceptionMap(2019,"视频id无效");
        }
        if (likeInfo.getUserId()==null || likeInfo.getUserId()==0){
            return ResultUtil.exceptionMap(2019,"短视频所属人id无效");
        }
        if (likeInfo.getFollowedId()==null || likeInfo.getFollowedId()==0 ){
            return ResultUtil.exceptionMap(2019,"点赞人id无效");
        }
        if (StringUtils.isEmpty(likeInfo.getFollName())){
            return ResultUtil.exceptionMap(2019,"点赞人姓名id无效");
        }
        try {
            //存储点赞信息到数据库
            LikeInfo selectOne = likeInfoService.getLikeInfo(likeInfo);
            if (selectOne!=null){
                return ResultUtil.exceptionMap(2022,"已点赞");
            }else {
                VideoDisplay videoDisplay = videoDisplayService.likeVideo(likeInfo.getVideoId());
                //保存关注数据
                likeInfo.setFollowedDate(new Date());
                likeInfoService.saveLikeInfo(likeInfo);
                LikeInfoVo likeInfoVo = new LikeInfoVo();
                likeInfoVo.setVideoId(videoDisplay.getVideoId());
                likeInfoVo.setIsLike(1);
                likeInfoVo.setVideoLikes(videoDisplay.getVideoLikes());
                return ResultUtil.successMap(JSON.toJSON(likeInfoVo));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.exceptionMap(2020,"存储数据失败");
        }
    }


    /**
     * 视频点赞
     * @param likeInfo
     * @return
     */
    @RequestMapping(value = "/cancelLikeVideo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="取消视频点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "videoId", value = "视频id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "followedId", value = "取消点赞人id", required = true, dataType = "String")
    })
    public Object cancelLikeVideo(LikeInfo likeInfo){
        if (likeInfo == null || likeInfo.getVideoId() == null || likeInfo.getVideoId() == 0){
            return ResultUtil.exceptionMap(2019,"视频id无效");
        }
        if (likeInfo.getFollowedId() == null){
            return ResultUtil.exceptionMap(2019,"取消点赞人id无效");
        }
        try {
            //查看点赞信息
            LikeInfo selectOne = likeInfoService.getLikeInfo(likeInfo);
            if (selectOne == null){
                return ResultUtil.exceptionMap(2022,"未点赞");
            }else {
                VideoDisplay videoDisplay = videoDisplayService.cancelLikeVideo(likeInfo.getVideoId());
                //删除关注信息
                likeInfoService.deleteLikeInfo(likeInfo);
                LikeInfoVo likeInfoVo = new LikeInfoVo();
                likeInfoVo.setVideoId(videoDisplay.getVideoId());
                likeInfoVo.setIsLike(0);
                likeInfoVo.setVideoLikes(videoDisplay.getVideoLikes());
                return ResultUtil.successMap(JSON.toJSON(likeInfoVo));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorMap();
        }
    }

}
