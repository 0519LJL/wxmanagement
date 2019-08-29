package com.iwei.wxmanagement.wx.video;

import com.iwei.wxmanagement.util.VideoUtil;
import com.iwei.wxmanagement.wx.video.model.Video;
import com.iwei.wxmanagement.wx.video.model.VideoDTO;
import com.iwei.wxmanagement.wx.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(description = "视频接口")
@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "获取视频", notes = "获取视频")
    @RequestMapping(value = "/getNewVideo.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getNewVideo(@RequestParam String password, @RequestParam String url) throws Exception {
        if (StringUtils.isEmpty(password) || !password.equals("L.1234556!")) {
            throw new Exception("请输入获取视频密码");
        }
        if (StringUtils.isEmpty(url)) {
            throw new Exception("没有视频获取入口");
        }

        List<VideoDTO> videoDTOList = VideoUtil.getDownloadUrl(url);
        int success = videoService.addVideo(videoDTOList);
        return "获取视频:" + videoDTOList.size() + "\n成功:" + success;
    }

    @ApiOperation(value = "获取视频(页)", notes = "获取视频(页)")
    @RequestMapping(value = "/getNewVideoPage.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getNewVideoPage(@RequestParam String password, @RequestParam String url) throws Exception {
        if (StringUtils.isEmpty(password) || !password.equals("L.1234556!")) {
            throw new Exception("请输入获取视频密码");
        }
        if (StringUtils.isEmpty(url)) {
            throw new Exception("没有视频获取入口");
        }

        List<VideoDTO> videoDTOList = VideoUtil.getDownloadUrlByPage(url);
        int success = videoService.addVideo(videoDTOList);
        return "获取视频:" + videoDTOList.size() + "\n成功:" + success;
    }

    @ApiOperation(value = "获取视频", notes = "获取视频")
    @RequestMapping(value = "/getVideo.html", method = {RequestMethod.GET})
    @ResponseBody
    public List<Video> getVideo(@RequestParam String name) throws Exception {

        if (StringUtils.isEmpty(name)) {
            throw new Exception("没有视频获取入口");
        }

        VideoDTO videoDTO = new VideoDTO();
        videoDTO.name = name;
        List<Video> videos = videoService.getVideoByName(videoDTO);
        List<Video> showVideos = new ArrayList<>();
        for (Video v : videos) {
            if(showVideos.size() < 5 && v.passcodeType == 0){
                showVideos.add(v);
            }
        }

        return  showVideos;
    }
}
