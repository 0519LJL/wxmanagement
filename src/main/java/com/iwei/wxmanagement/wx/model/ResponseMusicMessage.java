package com.iwei.wxmanagement.wx.model;

import lombok.Data;

@Data
public class ResponseMusicMessage extends ResponseBaseMessage  {

    //回复音乐
    private MusicModle Music;
}
