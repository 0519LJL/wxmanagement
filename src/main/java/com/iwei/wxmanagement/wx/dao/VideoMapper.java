package com.iwei.wxmanagement.wx.dao;


import com.iwei.wxmanagement.wx.video.model.Video;
import com.iwei.wxmanagement.wx.video.model.VideoDTO;

import java.util.List;

public interface VideoMapper {

    List<Video> getVideosByName(VideoDTO videoDTO);

    int insert(VideoDTO videoDTO);
}
