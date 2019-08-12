package com.iwei.wxmanagement.wx;

import com.iwei.wxmanagement.util.MessageUtil;
import com.iwei.wxmanagement.util.TenpayUtil;
import com.iwei.wxmanagement.wx.model.ArticleModel;
import com.iwei.wxmanagement.wx.model.ResponseImageMessage;
import com.iwei.wxmanagement.wx.model.ResponseNewsMessage;
import com.iwei.wxmanagement.wx.model.ResponseTextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class WeiXinHandler {
    private HttpServletRequest final_request;
    private HttpServletResponse final_response;
    private String TOKEN="bbf4008855171";

    /** 应答的参数 */
    private SortedMap parameters;

    /** 微信应答参数 */
    private SortedMap wxparameters;

    public	WeiXinHandler(HttpServletRequest request, HttpServletResponse response)
    {
        this.final_request=request;
        this.final_response=response;
        this.parameters = new TreeMap();
        this.wxparameters = new TreeMap();
    }


    public void init()
    {

        String xmlContent="";
        BufferedReader reader = null;
        String enc= TenpayUtil.getCharacterEncoding(final_request, final_response);

        try {
            final_request.setCharacterEncoding(enc);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        final_response.setCharacterEncoding(enc);
        doParse(final_request,enc);




    }
    public void responseTxt(WxRespSetting resp){
        String fromUserName = this.getWxparameter("FromUserName");
        String toUserName = this.getWxparameter("ToUserName");
        ResponseTextMessage text=new ResponseTextMessage();
        text.FromUserName = toUserName ;
        text.ToUserName = fromUserName;
        text.CreateTime =new Date().getTime();
        text.MsgType = MessageUtil.RESP_MESSAGE_TYPE_TEXT;
        text.Content = resp.title;

        String result=MessageUtil.textMessageToXml(text);
        System.out.println(result);
        this.print(result);

    }

    public void responseImage(String mediaId){
        String fromUserName = this.getWxparameter("FromUserName");
        String toUserName = this.getWxparameter("ToUserName");
        ResponseImageMessage response=new ResponseImageMessage();
        response.FromUserName= toUserName;
        response.ToUserName =fromUserName;
        response.CreateTime = new Date().getTime();

        response.MsgType = MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
        response.Image.MediaId =mediaId;

        String result=MessageUtil.textMessageToXml(response);
        System.out.println(result);
        this.print(result);

    }

    public void responseNews(List<WxRespSetting> respSettings)
    {
        ResponseNewsMessage rn=new ResponseNewsMessage();
        List<ArticleModel> list=new ArrayList<ArticleModel>();
        WxRespSetting retWxResp=respSettings.get(0);


        ArticleModel amTitle=new ArticleModel();
        amTitle.Title= retWxResp.title;
        amTitle.Description =retWxResp.descrtiptin;
        amTitle.PicUrl = retWxResp.picUrl;
        amTitle.Url = retWxResp.url;

        list.add(amTitle);

        int size=respSettings.size();
        if(size>1)
        {
            for(int i=1;i<size;i++)
            {
                WxRespSetting resp=respSettings.get(i);
                ArticleModel amNews=new ArticleModel();
                amNews.Title = resp.title;
                amNews.Description = resp.descrtiptin;
                amNews.PicUrl = resp.picUrl;
                amNews.Url = resp.url;
                list.add(amNews);
            }
        }
        String fromUserName = this.getWxparameter("FromUserName");
        String toUserName = this.getWxparameter("ToUserName");
        rn.FromUserName =toUserName;
        rn.ToUserName =fromUserName;
        rn.CreateTime = new Date().getTime();
        rn.MsgType =MessageUtil.RESP_MESSAGE_TYPE_NEWS;
        rn.ArticleCount =size;
        rn.Articles=list;

        String result=MessageUtil.newsMessageToXml(rn);
        this.print(result);


    }

    public void responseTxtNotClose(WxRespSetting resp){
        String fromUserName = this.getWxparameter("FromUserName");
        String toUserName = this.getWxparameter("ToUserName");
        ResponseTextMessage text=new ResponseTextMessage();
        text.FromUserName = toUserName ;
        text.ToUserName = fromUserName;
        text.CreateTime =new Date().getTime();
        text.MsgType = MessageUtil.RESP_MESSAGE_TYPE_TEXT;
        text.Content = resp.title;

        String result=MessageUtil.textMessageToXml(text);
        this.printButNotClose(result);

    }

    public void responseImageNotClose(String mediaId){
        String fromUserName = this.getWxparameter("FromUserName");
        String toUserName = this.getWxparameter("ToUserName");
        ResponseImageMessage response=new ResponseImageMessage();
        response.FromUserName= toUserName;
        response.ToUserName =fromUserName;
        response.CreateTime = new Date().getTime();

        response.MsgType = MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
        response.Image.MediaId =mediaId;

        String result=MessageUtil.textMessageToXml(response);
        System.out.println(result);
        this.printButNotClose(result);

    }

    public void responseNewsNotClose(List<WxRespSetting> respSettings)
    {
        ResponseNewsMessage rn=new ResponseNewsMessage();
        List<ArticleModel> list=new ArrayList<ArticleModel>();
        WxRespSetting retWxResp=respSettings.get(0);


        ArticleModel amTitle=new ArticleModel();
        amTitle.Title= retWxResp.title;
        amTitle.Description =retWxResp.descrtiptin;
        amTitle.PicUrl = retWxResp.picUrl;
        amTitle.Url = retWxResp.url;

        list.add(amTitle);

        int size=respSettings.size();
        if(size>1)
        {
            for(int i=1;i<size;i++)
            {
                WxRespSetting resp=respSettings.get(i);
                ArticleModel amNews=new ArticleModel();
                amNews.Title = resp.title;
                amNews.Description = resp.descrtiptin;
                amNews.PicUrl = resp.picUrl;
                amNews.Url = resp.url;
                list.add(amNews);
            }
        }
        String fromUserName = this.getWxparameter("FromUserName");
        String toUserName = this.getWxparameter("ToUserName");
        rn.FromUserName =toUserName;
        rn.ToUserName =fromUserName;
        rn.CreateTime = new Date().getTime();
        rn.MsgType =MessageUtil.RESP_MESSAGE_TYPE_NEWS;
        rn.ArticleCount =size;
        rn.Articles=list;

        String result=MessageUtil.newsMessageToXml(rn);
        this.printButNotClose(result);


    }



    private void doParse(HttpServletRequest request,String enc)
    {
        String postStr=null;
        try{
            postStr=this.readStreamParameter(request.getInputStream(),enc);
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println(postStr);
        if (null!=postStr&&!postStr.isEmpty()){
            Document document=null;
            try{
                document = DocumentHelper.parseText(postStr);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(null==document){
                this.print("");
                return;
            }
            Element root=document.getRootElement();
            Iterator it  = root.elementIterator();
            while(it.hasNext())
            {
                Element e=(Element)it.next();
                String k = e.getName();
                String v = e.getText();
                this.setWxparameter(k, v);
            }
        }

    }


    //微信接口验证
    public boolean checkSignature(){
        String signature = final_request.getParameter("signature");
        String timestamp = final_request.getParameter("timestamp");
        String nonce = final_request.getParameter("nonce");
        String token=TOKEN;
        String[] tmpArr={token,timestamp,nonce};
        Arrays.sort(tmpArr);
        String tmpStr=this.ArrayToString(tmpArr);
        tmpStr=this.SHA1Encode(tmpStr);
        if(tmpStr.equalsIgnoreCase(signature)){
            return true;
        }else{
            return false;
        }
    }
    //向请求端发送返回数据
    public void print(String content){
        try{
            final_response.getWriter().print(content);
            final_response.getWriter().flush();
            final_response.getWriter().close();
        }catch(Exception e){

        }
    }

    //向请求端发送返回数据但是先别关闭，稍后我会主动关闭
    public void printButNotClose(String content){
        try{
            final_response.getWriter().print(content);
            final_response.getWriter().flush();
        }catch(Exception e){

        }
    }

    public void close(){
        try{
            final_response.getWriter().close();
        }catch(Exception e){

        }
    }


    //数组转字符串
    public String ArrayToString(String [] arr){
        StringBuffer bf = new StringBuffer();
        for(int i = 0; i < arr.length; i++){
            bf.append(arr[i]);
        }
        return bf.toString();
    }
    //sha1加密
    public String SHA1Encode(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }
    public final String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString().toUpperCase();
    }
    //从输入流读取post参数
    public String readStreamParameter(ServletInputStream in, String enc){
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader=null;
        try{
            reader = new BufferedReader(new InputStreamReader(in,enc));
            String line=null;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null!=reader){
                try {
                    reader.close();
                    reader=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }


    public void responseNull(){

        String result="";
        this.print(result);

    }

    /**
     * 获取微信参数值
     * @param parameter 参数名称
     * @return String
     */
    public String getWxparameter(String wxparameter) {
        String s = (String)this.wxparameters.get(wxparameter);
        return (null == s) ? "" : s;
    }

    /**
     * 设置微信参数值
     * @param parameter 参数名称
     * @param parameterValue 参数值
     */
    public void setWxparameter(String wxparameter, String wxparameterValue) {
        String v = "";
        if(null != wxparameterValue) {
            v = wxparameterValue.trim();
        }
        this.wxparameters.put(wxparameter, v);
    }

    /**
     * 返回微信所有的参数
     * @return SortedMap
     */
    public SortedMap getAllWxparameters() {
        return this.wxparameters;
    }

    public HttpServletRequest getFinal_request() {
        return final_request;
    }
}
