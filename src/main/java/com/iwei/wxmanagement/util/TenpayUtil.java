package com.iwei.wxmanagement.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TenpayUtil {
    private static Object Server;
    private static String QRfromGoogle;

    /**
     * 获取编码字符集
     * @param request
     * @param response
     * @return String
     */

    public static String getCharacterEncoding(HttpServletRequest request,
                                              HttpServletResponse response) {

        if(null == request || null == response) {
            return "gbk";
        }

        String enc = request.getCharacterEncoding();
        if(null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if(null == enc || "".equals(enc)) {
            enc = "gbk";
        }

        return enc;
    }
}
