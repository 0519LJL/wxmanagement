package com.iwei.wxmanagement.wx.model;

import lombok.Data;

@Data
public class ResponseTextMessage extends ResponseBaseMessage {
    //回复的消息内容
    public String Content;

}
