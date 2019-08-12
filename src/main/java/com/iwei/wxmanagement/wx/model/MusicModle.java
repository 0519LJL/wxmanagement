package com.iwei.wxmanagement.wx.model;

import lombok.Data;

@Data
public class MusicModle {
    // 音乐名称
    public String Title;
    // 音乐描述
    public String Description;
    // 音乐链接
    public String MusicUrl;
    // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
    public String HQMusicUrl;
}
