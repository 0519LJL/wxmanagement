package com.iwei.wxmanagement.wx.video.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class VideoDTO {
    public int vid;
    public String name;
    public int movieType;
    public String part;
    public String url;
    public int viewNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public String createTime;

    public int passcodeType;

    public VideoDTO(){}

    public VideoDTO(String name,int movieType,String url){
        this.name = name;
        this.movieType = movieType;
        this.url = url;
    }
    public VideoDTO(String name,int movieType,String url,int passcodeType){
        this.name = name;
        this.movieType = movieType;
        this.url = url;
        this.passcodeType=passcodeType;
    }
}
