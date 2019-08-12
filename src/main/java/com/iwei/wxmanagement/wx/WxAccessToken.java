package com.iwei.wxmanagement.wx;

import lombok.Data;

@Data
public class WxAccessToken {
    public String accessToken;

    public Integer expiresIn;

    public Long startTime;

    public String refreshToken;

    public String openid;

    public String scope;

    public Integer id;
}
