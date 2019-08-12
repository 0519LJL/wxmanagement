package com.iwei.wxmanagement.wx.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseNewsMessage extends ResponseBaseMessage {

    // 图文消息个数，限制为10条以内
    public int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    public List<ArticleModel> Articles;
}
