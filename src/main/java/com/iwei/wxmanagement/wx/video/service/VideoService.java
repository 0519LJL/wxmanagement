package com.iwei.wxmanagement.wx.video.service;

import com.iwei.wxmanagement.wx.video.model.Video;
import com.iwei.wxmanagement.wx.video.model.VideoDTO;

import java.util.List;

public interface VideoService {

    List<Video> getVideoByName(VideoDTO videoDTO);

    int addVideo(List<VideoDTO> videoDTOList);
}
