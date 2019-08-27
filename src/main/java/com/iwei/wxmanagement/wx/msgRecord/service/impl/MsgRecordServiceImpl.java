package com.iwei.wxmanagement.wx.msgRecord.service.impl;

import com.iwei.wxmanagement.wx.dao.MsgRecordMapper;
import com.iwei.wxmanagement.wx.member.dto.MemberDTO;
import com.iwei.wxmanagement.wx.member.model.Member;
import com.iwei.wxmanagement.wx.member.service.MemberService;
import com.iwei.wxmanagement.wx.msgRecord.dto.MsgRecordDTO;
import com.iwei.wxmanagement.wx.msgRecord.model.MsgRecord;
import com.iwei.wxmanagement.wx.msgRecord.service.MsgRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MsgRecordServiceImpl implements MsgRecordService {
    private static Logger logger = Logger.getLogger(MsgRecordServiceImpl.class);

    @Autowired
    private MsgRecordMapper msgRecordMapper;

    @Autowired
    private MemberService memberService;

    @Override
    public MsgRecord addMsgRecord(MsgRecord record){
        MsgRecordDTO recordDTO = new MsgRecordDTO();
        recordDTO.openid = record.openid;
        recordDTO.type = record.type;
        recordDTO.comment = record.comment;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        recordDTO.createTime = sdf.format(new Date());

        if(msgRecordMapper.insert(recordDTO) >0){
            record.msgid = recordDTO.msgid;
        }
        return record;
    }

    @Override
    public void addMsgRecord(int type, String openid,String comment){

        //检查用户是否存在
        MemberDTO memberDTO = memberService.selectByOpenId(openid);
        if(memberDTO == null){
            Member member = new Member();
            member.wxOpenid = openid;
            member.enable = 1;
            member.verifyMobile= 0;
            memberService.addMember(member);
        }

        MsgRecordDTO recordDTO = new MsgRecordDTO();
        recordDTO.openid = openid;
        recordDTO.type = type;
        recordDTO.comment = comment;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        recordDTO.createTime = sdf.format(new Date());
        msgRecordMapper.insert(recordDTO);
    }
}
