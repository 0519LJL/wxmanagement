package com.iwei.wxmanagement.wx.model;

import lombok.Data;

@Data
public class ArticleModel {
    // 图文消息名称
    public String Title;
    // 图文消息描述
    public String Description;
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致
    public String PicUrl;
    // 点击图文消息跳转链接
    public String Url;
}
