package com.iwei.wxmanagement.wx.video.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Video {
    public int vid;
    public String name;
    public int movieType;
    public String part;
    public String url;
    public int viewNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public String createTime;
}
