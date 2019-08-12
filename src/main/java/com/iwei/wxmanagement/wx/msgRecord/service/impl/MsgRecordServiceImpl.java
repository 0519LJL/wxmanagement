package com.iwei.wxmanagement.wx.msgRecord.service.impl;

import com.iwei.wxmanagement.wx.dao.MsgRecordMapper;
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

    @Override
    public MsgRecord addMsgRecord(MsgRecord record){
        MsgRecordDTO recordDTO = new MsgRecordDTO();
        recordDTO.openid = record.openid;
        recordDTO.type = 0;
        recordDTO.comment = record.comment;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        recordDTO.createTime = sdf.format(new Date());

        if(msgRecordMapper.insert(recordDTO) >0){
            record.msgid = recordDTO.msgid;
        }
        return record;
    }
}
