package com.iwei.wxmanagement.wx;

import com.iwei.wxmanagement.util.MessageUtil;
import com.iwei.wxmanagement.wx.memberQrCode.service.MemberQrCodeService;
import com.iwei.wxmanagement.wx.msgRecord.model.MsgRecord;
import com.iwei.wxmanagement.wx.msgRecord.service.MsgRecordService;
import com.iwei.wxmanagement.wx.video.model.Video;
import com.iwei.wxmanagement.wx.video.model.VideoDTO;
import com.iwei.wxmanagement.wx.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Api(description = "微信接口")
@Controller
public class wxController {

    @Autowired
    private wxService wxservice;

    @Autowired
    private MemberQrCodeService memberQrCodeService;

    @Autowired
    private MsgRecordService msgRecordService;

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "获取accessToken", notes = "获取accessToken")
    @RequestMapping(value = "/getAccessToken.html", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public WxAccessToken getAccessToken() {
        return wxservice.getAccessToke();
    }

    @ApiOperation(value = "获取微信消息", notes = "获取微信消息")
    @RequestMapping(value = "/getRequest", method = {RequestMethod.GET, RequestMethod.POST})
    public WxAccessToken getRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WeiXinHandler handler = new WeiXinHandler(request, response);
        handler.init();
        String msgType = handler.getWxparameter("MsgType");
        String fromUserName = handler.getWxparameter("FromUserName");
        if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
            String content = handler.getWxparameter("Content") != null ? handler.getWxparameter("Content") : "";
            WxRespSetting wxresp = new WxRespSetting();
            wxresp.keyWord = content;
            List<WxRespSetting> list = new ArrayList<WxRespSetting>();
            WxRespSetting contect = new WxRespSetting();

            //插入查询记录
            msgRecordService.addMsgRecord(1,fromUserName,content);

            switch (content) {
//                case "1":
//                    contect.menuId = -4;
//                    contect.messageType = "text";
//                    contect.title = "<a href=\"\"http://m.800pharm.com/delivery\"\">点我可查询订单/物流喔</a>";
//                    list.add(contect);
//
//                    handleEvent(handler, list);
//                    break;
//                case "2":
//                    contect.messageType = "news";
//                    contect.title = "测试";
//                    contect.descrtiptin = "测试";
//                    contect.picUrl = "https://mmbiz.qpic.cn/mmbiz_jpg/MILnnOrpvzPQ5xuD8JxzWt1Sd1eUwTf3xCY7nLicDubJULwOLKVcuGOJ64uERU9lACN2AhPD0hUia7IILejv0bVg/0?wx_fmt=jpeg";
//                    contect.url = "http://www.800pharm.com/";
//                    list.add(contect);
//                    handleEvent(handler, list);
//                    break;
                case "我要加速":
                    contect.messageType = "static/image";
                    list.add(contect);
                    handleEvent(handler, list);
                    break;

//                case "4":
//                    String imgPath = wxservice.CreatQRCodeImage(request, fromUserName);
//                    if (!StringUtils.isEmpty(imgPath)) {
//                        String homePath = request.getScheme() + "://" + request.getServerName() + "/" + request.getContextPath();
//                        contect.messageType = "news";
//                        contect.title = "我的二维码";
//                        contect.descrtiptin = "轻松成为代理,马上转发获得可观分成.";
//                        contect.picUrl = "https://mmbiz.qpic.cn/mmbiz_jpg/MILnnOrpvzPQ5xuD8JxzWt1Sd1eUwTf3xCY7nLicDubJULwOLKVcuGOJ64uERU9lACN2AhPD0hUia7IILejv0bVg/0?wx_fmt=jpeg";
//                        contect.url = homePath + "/getQrCode.html?userId=" + fromUserName + "&type=0";
//                        list.add(contect);
//                        handleEvent(handler, list);
//                    }
//                    handler.responseNull();
//                    break;
//
//                case "5":
//                    String videoPath = wxservice.CreatQRCodeImage(request, fromUserName);
//                    if (!StringUtils.isEmpty(videoPath)) {
//                        String homePath = request.getScheme() + "://" + request.getServerName() + "/" + request.getContextPath();
//                        contect.messageType = "news";
//                        contect.title = "战狼";
//                        contect.descrtiptin = "战狼";
//                        contect.picUrl = "https://mmbiz.qpic.cn/mmbiz_jpg/MILnnOrpvzPQ5xuD8JxzWt1Sd1eUwTf3xCY7nLicDubJULwOLKVcuGOJ64uERU9lACN2AhPD0hUia7IILejv0bVg/0?wx_fmt=jpeg";
//                        contect.url = homePath + "/getModelView.html";
//                        list.add(contect);
//                        handleEvent(handler, list);
//                    }
//                    handler.responseNull();
//                    break;
                default:
                    VideoDTO videoDTO = new VideoDTO();
                    videoDTO.name = content;
                    List<Video> videos = videoService.getVideoByName(videoDTO);
                    List<Video> showVideos = new ArrayList<>();
                    for (Video v : videos) {
                        if(showVideos.size() < 5 && v.passcodeType == 0){
                            showVideos.add(v);
                        }
                    }

                    if(showVideos.size() >0){
                        String contentStr = "检索到\""+content+"\"相关影片如下:\n";

                        for (int i = 0; i < showVideos.size(); i++) {
                            contentStr += "\n"+showVideos.get(i).name +"\n迅雷下载地址:"+ showVideos.get(i).url+"\n";
                        }
                        if(videos.size() > 5){
                            contentStr +="\n更多影集请添加微信:\"LZ_7878\"咨询.";
                        }
                        contect.messageType = "text";
                        contect.title = contentStr;
                        list.add(contect);

                        handleEvent(handler, list);
                    }else if (showVideos.size() == 0 && videos.size() >0){
                        contect.messageType = "text";
                        contect.title = "您还没有权限查看\""+content+"\".\n请添加微信:\"LZ_7878\"开通权限.";
                        list.add(contect);

                        handleEvent(handler, list);
                    }else {
                        contect.messageType = "text";
                        contect.title = "没有检索到\"" + content + "\"相关影片.\n更多影集请添加微信:\"LZ_7878\"咨询.";
                        list.add(contect);

                        handleEvent(handler, list);
                    }

                    break;
            }


        } else if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
            // 事件类型
            String eventType = handler.getWxparameter("Event");
            String eventKey = handler.getWxparameter("EventKey");
            if (!StringUtils.isEmpty(eventKey)) {
                System.out.println(eventKey);
            }
            switch (eventType){
                case MessageUtil.EVENT_TYPE_SUBSCRIBE:
                    wxservice.subscribeGreet(handler);
                    break;
                case MessageUtil.EVENT_TYPE_UNSUBSCRIBE :
                    wxservice.deleteMember(handler);
                    break;

            }
        } else if (MessageUtil.REQ_MESSAGE_TYPE_VOICE.equals(msgType) || MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {
        } else {
            if (handler.checkSignature()) {
                handler.print(request.getParameter("echostr"));
            } else {
            }
        }

        return null;
    }

    @ApiOperation(value = "打开页面", notes = "打开页面")
    @RequestMapping(value = "/getModelView.html", method = {RequestMethod.GET})
    public String getModelView(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        map.put("imgUrl", "https://mmbiz.qpic.cn/mmbiz_jpg/MILnnOrpvzPQ5xuD8JxzWt1Sd1eUwTf3xCY7nLicDubJULwOLKVcuGOJ64uERU9lACN2AhPD0hUia7IILejv0bVg/0?wx_fmt=jpeg");
        return "index";
    }

    @ApiOperation(value = "查看二维码", notes = "查看二维码")
    @RequestMapping(value = "/getQrCode.html", method = {RequestMethod.GET})
    public String getQrCode(@RequestParam String userId, @RequestParam int type) {
        System.out.println("userId :" + userId + "type:" + type);
        return "qrCode";
    }

    @ApiOperation(value = "打开视频页面", notes = "打开视频页面")
    @RequestMapping(value = "/getMovieView.html", method = {RequestMethod.GET})
    public String getMovieView(HashMap<String, Object> map) {
        return "viewMovie";
    }

    @ApiOperation(value = "查看影片", notes = "查看影片")
    @RequestMapping(value = "/getView.html", method = {RequestMethod.GET})
    @ResponseBody
    public void getView(HttpServletResponse response) {
        show(response, "video");
    }

    public void show(HttpServletResponse response, String type) {
        try {
            //本地路径
            String fileName = "D:/video/";
            File file = new File(fileName);
            //所有文件，文件夹集合
            File[] tempList = file.listFiles();
            //筛选出文件与后缀名为mp4视频文件得到文件名的list集合
            List<String> fileNameList1 = Arrays.asList(tempList).stream()
                    .filter(f -> f.isFile() && f.getName().endsWith("mp4"))
                    .map(File::getName).collect(Collectors.toList());
            int c = 0;
            //取得当前文件名
            String string = fileNameList1.get(c);
            //从头播放
            if (++c == fileNameList1.size())
                c = 0;
            // 以byte流的方式打开文件
            FileInputStream fis = new FileInputStream(fileName + string);
            // 得到文件大小
            int i = fis.available();
            byte data[] = new byte[i];
            // 读数据
            fis.read(data);
            // 设置返回的文件类型
            response.setContentType(type + "/*");
            // 得到向客户端输出二进制数据的对象
            OutputStream toClient = response.getOutputStream();
            // 输出数据
            toClient.write(data);
            toClient.flush();
            toClient.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件不存在");
        }
    }

    @ApiOperation(value = "获取二维码", notes = "获取二维码")
    @RequestMapping(value = "/getQrCodeByUserId.html", method = {RequestMethod.GET})
    public void getQrCodeByUserId(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId, @RequestParam int type) {
        WeiXinHandler handler = new WeiXinHandler(request, response);
        handler.init();
        String msgType = handler.getWxparameter("MsgType");
        memberQrCodeService.getQrCodeByUserIdType(response, userId, type);
    }

    private void handleEvent(WeiXinHandler handler, List<WxRespSetting> list) {
        int loop = 0;
        for (WxRespSetting retWxResp : list) {
            loop++;
            if (loop == list.size()) {
                if (MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(retWxResp.messageType)) {
                    handler.responseTxt(retWxResp);
                } else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(retWxResp.messageType)) {
                    handler.responseNews(list);
                } else if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE.equals(retWxResp.messageType)) {
                    handler.responseImage("1ZlPhQJYpORt4PTDsAv33NeGmok2MYNy-9CFDCRyaxQ");
                } else {
                    handler.responseNull();
                }
            } else {
                if (MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(retWxResp.messageType)) {
                    handler.responseTxtNotClose(retWxResp);
                } else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(retWxResp.messageType)) {
                    handler.responseNewsNotClose(list);
                } else if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE.equals(retWxResp.messageType)) {
                    handler.responseImageNotClose("1ZlPhQJYpORt4PTDsAv33NeGmok2MYNy-9CFDCRyaxQ");
                } else {
                    handler.responseNull();
                }
            }
        }
    }
}
