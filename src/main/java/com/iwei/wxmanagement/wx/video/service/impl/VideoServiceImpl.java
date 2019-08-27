package com.iwei.wxmanagement.wx.video.service.impl;

import com.iwei.wxmanagement.wx.dao.VideoMapper;
import com.iwei.wxmanagement.wx.video.model.Video;
import com.iwei.wxmanagement.wx.video.model.VideoDTO;
import com.iwei.wxmanagement.wx.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public int addVideo(List<VideoDTO> videoDTOList){
        int count = 0;
        int type =  (videoDTOList.size() > 5 ? 1 : 0);
        for (VideoDTO videoDTO : videoDTOList) {
            videoDTO.movieType=type;
            videoDTO.viewNum = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            videoDTO.createTime = sdf.format(new Date());
            count += videoMapper.insert(videoDTO);
        }

        return count;
    }

}
