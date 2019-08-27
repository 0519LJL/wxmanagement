package com.iwei.wxmanagement.wx.video.service.impl;

import com.iwei.wxmanagement.wx.dao.VideoMapper;
import com.iwei.wxmanagement.wx.video.model.Video;
import com.iwei.wxmanagement.wx.video.model.VideoDTO;
import com.iwei.wxmanagement.wx.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> getVideoByName(VideoDTO videoDTO){
        List<Video> videoList = videoMapper.getVideosByName(videoDTO);
        return videoList;
    }

}
