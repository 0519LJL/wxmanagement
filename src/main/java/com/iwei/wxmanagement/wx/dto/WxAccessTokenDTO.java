package com.iwei.wxmanagement.wx.dto;

import lombok.Data;

@Data
public class WxAccessTokenDTO {
    public int id;
    public String accessToken;
    public int expiresIn;
    public Long startTime;
    public String refreshToken;
    public String openId;
    public String scope;
}
