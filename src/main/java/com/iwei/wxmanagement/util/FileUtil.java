package com.iwei.wxmanagement.util;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class FileUtil {

    //获取static绝对路径
    public static String getStaticPath() {

        String path = null;
        try {
            String serverpath = ResourceUtils.getURL("classpath:static").getPath().replace("%20", " ").replace('/', '\\');
            path = serverpath.substring(1);//从路径字符串中取出工程路径
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path;
    }
}

