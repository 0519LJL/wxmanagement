package com.iwei.wxmanagement.wx.model;

import lombok.Data;

@Data
public class ResponseImageMessage extends ResponseBaseMessage {
    public ResponseImageMessage(){
        this.Image = new Image();
    }

    //通过素材管理中的接口上传多媒体文件，得到的id。
    public Image Image;
}
