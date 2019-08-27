package com.iwei.wxmanagement.wx.member.service;

import com.iwei.wxmanagement.wx.member.dto.MemberDTO;
import com.iwei.wxmanagement.wx.member.model.Member;

public interface MemberService {

    Member getMemberById(int mid);

    MemberDTO selectByOpenId(String wxOpenid);

    Member addMember(Member member);

    Member updateMember(Member member);
}
