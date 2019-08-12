package com.iwei.wxmanagement.wx.dao;

import com.iwei.wxmanagement.wx.memberQrCode.dto.MemberQrCodeDTO;

public interface MemberQrCodeMapper {

    MemberQrCodeDTO selectByPrimaryKey(Integer id);


    MemberQrCodeDTO selectBymid(MemberQrCodeDTO qrCodeDTO);
}
