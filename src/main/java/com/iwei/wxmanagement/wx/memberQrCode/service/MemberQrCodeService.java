package com.iwei.wxmanagement.wx.memberQrCode.service;

import com.iwei.wxmanagement.wx.memberQrCode.model.MemberQrCode;

import javax.servlet.http.HttpServletResponse;

public interface MemberQrCodeService {

    MemberQrCode getMemberById(MemberQrCode qrCode);

    void getQrCodeByUserIdType(HttpServletResponse response, String userId, int type);
}
