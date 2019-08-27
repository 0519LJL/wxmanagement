package com.iwei.wxmanagement.wx.member.model;

import lombok.Data;

@Data
public class Member {
    public int mid;
    public String name;
    public String address;
    public String postCode;
    public String mobile;
    public int verifyMobile;
    public String wxOpenid;
    public String wxName;
    public String createTime;
    public int enable;
}
