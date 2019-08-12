package com.iwei.wxmanagement.wx.member.service.impl;

import com.iwei.wxmanagement.wx.dao.MemberMapper;
import com.iwei.wxmanagement.wx.member.dto.MemberDTO;
import com.iwei.wxmanagement.wx.member.model.Member;
import com.iwei.wxmanagement.wx.member.service.MemberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {
    private static Logger logger = Logger.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member getMemberById(int mid){
        Member member = new Member();
        MemberDTO memberDTO = memberMapper.selectByPrimaryKey(mid);
        if(memberDTO != null){
            member.mid = memberDTO.mid;
            member.name = memberDTO.name;
            member.address = memberDTO.address;
            member.postCode = memberDTO.postCode;
            member.mobile = memberDTO.mobile;
            member.verifyMobile = memberDTO.verifyMobile;
            member.wxName = memberDTO.wxName;
            member.wxOpenid = memberDTO.wxOpenid;
        }
        return  member;
    }

    @Override
    public Member addMember(Member member){
        if(member == null){
            return member;
        }

        MemberDTO memberDTO = memberMapper.selectByOpenId(member.wxOpenid);
        if(memberDTO != null){
            member.mid = memberDTO.mid;
            member.wxOpenid = memberDTO.wxOpenid;
            member.wxName = memberDTO.wxName;
            member.createTime = memberDTO.createTime;
            return member;
        }
        memberDTO = new MemberDTO();
        memberDTO.wxOpenid = member.wxOpenid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        memberDTO.createTime = sdf.format(new Date());
        memberMapper.insert(memberDTO);
        member.mid = memberDTO.mid;
        return  member;
    }
}
