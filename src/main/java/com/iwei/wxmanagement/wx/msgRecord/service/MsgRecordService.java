package com.iwei.wxmanagement.wx.msgRecord.service;

import com.iwei.wxmanagement.wx.msgRecord.model.MsgRecord;

public interface MsgRecordService {

    MsgRecord addMsgRecord(MsgRecord record);

    void addMsgRecord(int type, String openid,String comment);
}
