package com.iwei.wxmanagement.wx.member.service;

import com.iwei.wxmanagement.wx.member.model.Member;

public interface MemberService {

    Member getMemberById(int mid);

    Member addMember(Member member);
}
