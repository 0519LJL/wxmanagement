package com.iwei.wxmanagement.wx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface wxService {

    WxAccessToken getAccessToke();

    String CreatQRCodeImage(HttpServletRequest request,String fromUserName) throws Exception;

    void subscribeGreet(WeiXinHandler handler);
}
