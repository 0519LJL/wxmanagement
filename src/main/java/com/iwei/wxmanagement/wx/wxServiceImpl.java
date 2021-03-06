package com.iwei.wxmanagement.wx;

import com.alibaba.fastjson.JSONObject;
import com.iwei.wxmanagement.util.FileUtil;
import com.iwei.wxmanagement.util.MessageUtil;
import com.iwei.wxmanagement.util.QRCodeUtil;
import com.iwei.wxmanagement.util.wxInfo;
import com.iwei.wxmanagement.wx.dao.WxAccessTokenDaoMapper;
import com.iwei.wxmanagement.wx.dto.WxAccessTokenDTO;
import com.iwei.wxmanagement.wx.member.model.Member;
import com.iwei.wxmanagement.wx.member.service.MemberService;
import com.iwei.wxmanagement.wx.msgRecord.model.MsgRecord;
import com.iwei.wxmanagement.wx.msgRecord.service.MsgRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class wxServiceImpl implements wxService {
    private static Logger log = Logger.getLogger(wxServiceImpl.class);

    @Autowired
    private wxInfo wxInfo;

    @Autowired
    private WxAccessTokenDaoMapper wxAccessTokenDaoMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MsgRecordService msgRecordService;

    @Override
    public WxAccessToken getAccessToke() {
        WxAccessToken accessToken = new WxAccessToken();

        WxAccessTokenDTO wxAccessToken =  wxAccessTokenDaoMapper.selectByPrimaryKey(1);
        if(wxAccessToken != null && !checkTokenValidate(wxAccessToken)){
            String requestUrl = wxInfo.access_token_url.replace("APPID", wxInfo.appID).replace("APPSECRET", wxInfo.appSecret);
            JSONObject jsonObject = wxInfo.httpsRequest(requestUrl, "GET", null);
            wxAccessToken.accessToken = jsonObject.getString("access_token");
            wxAccessToken.expiresIn = Integer.parseInt(jsonObject.getString("expires_in"));
            wxAccessToken.startTime = System.currentTimeMillis();
            wxAccessTokenDaoMapper.updateByPrimaryKey(wxAccessToken);
        }

        if (wxAccessToken != null) {
            try {

                accessToken.accessToken = wxAccessToken.accessToken;
                accessToken.expiresIn = wxAccessToken.expiresIn;
                accessToken.startTime = wxAccessToken.startTime;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }

    @Override
    public void subscribeGreet(WeiXinHandler handler){
        String fromUserName = handler.getWxparameter("FromUserName");
        boolean isBack = false;
        /*1.根据用户openID获取用户信息*/

        /*2.关注用户信息保存到数据库*/
        //服务号获取不了用户信息只能保存openid
        Member member = new Member();
        member.wxOpenid = handler.getWxparameter("FromUserName");
        member = memberService.addMember(member);

        //重新关注用户需要设置为启用并记录日志为重新关注
        if(member!=null && member.enable ==0){
            isBack = true;
            member.enable =1;
            memberService.updateMember(member);

        }

        //3插入查询记录
        String comment = "关注公众号";
        if(isBack) comment = "重新关注公众号";
        msgRecordService.addMsgRecord(0,fromUserName,comment);

        /*4.回复用户招呼信息*/
        List<WxRespSetting> list = new ArrayList<WxRespSetting>();
        WxRespSetting contect = new WxRespSetting();
        contect.messageType = "text";
        if(isBack){
            contect.title = "欢迎回来!\n【发送电影名称即可获取相对应视频下载链接，如“哪吒”.】" +
                    "\n(因公众号服务仍在服务调试阶段,公众号仍属正常运作请勿取消关注.)";
        }else{
            contect.title = "您好,欢迎来到电影123的世界!\n【发送电影名称即可获取相对应视频下载链接，如“哪吒”.】" +
                    "\n(因公众号服务仍在服务调试阶段,公众号仍属正常运作请勿取消关注.)";
        }
        list.add(contect);
        handleEvent(handler, list);
    }

    @Override
    public void deleteMember (WeiXinHandler handler){
        String fromUserName = handler.getWxparameter("FromUserName");
        Member member = new Member();
        member.wxOpenid = fromUserName;
        member.enable =0;
        memberService.updateMember(member);

        msgRecordService.addMsgRecord(0,fromUserName,"取消关注公众号");

        /*3.回复用户招呼信息*/
        List<WxRespSetting> list = new ArrayList<WxRespSetting>();
        WxRespSetting content = new WxRespSetting();
        content.messageType = "text";
        content.title = "感谢您的一路陪伴,再见!";
        list.add(content);
        handleEvent(handler, list);
    }

    private void handleEvent(WeiXinHandler handler, List<WxRespSetting> list) {
        for (WxRespSetting retWxResp : list) {
            if (MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(retWxResp.messageType)) {
                handler.responseTxt(retWxResp);
            } else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(retWxResp.messageType)) {

                handler.responseNews(list);
            } else if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE.equals(retWxResp.messageType)) {
                handler.responseImage("1ZlPhQJYpORt4PTDsAv33NeGmok2MYNy-9CFDCRyaxQ");
            } else {
                handler.responseNull();
            }
        }
    }

    /**
     * 检查accessToken是否在有效期内
     * @param tokenDTO
     * @return
     */
    private boolean checkTokenValidate(WxAccessTokenDTO tokenDTO)
    {

        return ( tokenDTO!=null && (System.currentTimeMillis()-tokenDTO.startTime)/1000 <tokenDTO.expiresIn);

    }

    @Override
    public String CreatQRCodeImage(HttpServletRequest request, String fromUserName) throws Exception {
        log.info("This is info CreatQRCodeImage.");
        try {
            String serverpath= FileUtil.getStaticPath();
            String headPath = serverpath + "/image/base/head.jpg";
            String qrPath = serverpath + "/image/qrCodes/" + fromUserName + ".png";
            if( new File(qrPath).exists()){
                return qrPath;
            }
            QRCodeUtil.encode("http://800pharm.com", headPath, qrPath, true);
            return qrPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
