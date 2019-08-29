package com.iwei.wxmanagement.util;

import com.iwei.wxmanagement.wx.video.model.VideoDTO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtil {
    private  static  String hostUrl = "http://www.dy2046.net/";
    public static String getURLInfo(String urlInfo) throws Exception {
        //读取目的网页URL地址，获取网页源码
        URL url = new URL(urlInfo);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36"); //防止报403错误
        String encode = httpUrl.getContentEncoding();
        InputStream is = httpUrl.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        if (encode == null) {
            br = new BufferedReader(new InputStreamReader(is, "gbk"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        br.close();
        //获得网页源码
        return sb.toString().trim();
    }

    //获取下载地址
    public static List<VideoDTO> getDownloadUrl(String url) throws Exception {
        String str = getURLInfo(url);

        Pattern proInfo = Pattern.compile("<div class=\"down_list\">(.*?)</ul></div>");
        Matcher matcher_a = proInfo.matcher(str);
        List<VideoDTO> videoDTOList = new ArrayList<>();
        List<String> downloadUrl = new ArrayList<>();

        while (matcher_a.find()) {
            downloadUrl.add(matcher_a.group(1));
//            for (int i=0;i<= matcher_a.groupCount();i++){
//                System.out.println(i+":"+matcher_a.group(i));
//            }
        }


        if (downloadUrl.size() > 2) {
            proInfo = Pattern.compile("class=\"down_url\" value=\"(.*?)\" file_name=\"(.*?)\"");
            matcher_a = proInfo.matcher(downloadUrl.get(2));
            while (matcher_a.find()) {
                String name = matcher_a.group(2).replace("[迅雷下载www.DY2046.Net]", "").replace("[www.DY2046.Net]","");
                int  movieType = checkType(name) ? 1 : 0;
                int passCodeType = checkPassType(name);
                videoDTOList.add(new VideoDTO(name,movieType,matcher_a.group(1),passCodeType));
            }
        } else {
            for (String downUrl : downloadUrl) {
                proInfo = Pattern.compile("class=\"down_url\" value=\"(.*?)\" file_name=\"(.*?)\"");
                matcher_a = proInfo.matcher(downUrl);
                while (matcher_a.find()) {
                    String name = matcher_a.group(2).replace("[迅雷下载www.DY2046.Net]", "").replace("[www.DY2046.Net]","");
                    int  movieType = checkType(name) ? 1 : 0;
                    int passCodeType = checkPassType(name);
                    videoDTOList.add(new VideoDTO(name,movieType,matcher_a.group(1),passCodeType));
                }
            }
        }
        return videoDTOList;

    }

    private static boolean checkType(String name){
        Pattern proInfo = Pattern.compile("第(\\d+?)集");
        Matcher matcher_a = proInfo.matcher(name);
        return  matcher_a.find();
    }

    private static int checkPassType(String name){
        int passcodeType = 0;
        Pattern proInfo = Pattern.compile("第(\\d+?)集");
        Matcher matcher_a = proInfo.matcher(name);
        while (matcher_a.find()){
           int page = Integer.parseInt(matcher_a.group(1).toString());
           if(page> 5){
               passcodeType = 1;
           }
        }
        return passcodeType;
    }


    //获取根据分页获取下载地址
    public static List<VideoDTO> getDownloadUrlByPage(String url) throws Exception {
        String str = getURLInfo(url);

        //获取影片模块
        Pattern proInfo = Pattern.compile("<div class=\"con\">(.*?)</ul>.*?/(\\d+?)页");
        Matcher matcher_a = proInfo.matcher(str);
        String name = "";
        int page = 0;
        while (matcher_a.find()) {
            name =matcher_a.group(1);
        }

        //获取页面影片地址
        List<String> urlList = new ArrayList<>();
        proInfo = Pattern.compile("<h3 class=\"c0071bc\".*?<a href=\"(.*?)\" target=\"_blank\">(.*?)</a>");
        matcher_a = proInfo.matcher(name);
        while (matcher_a.find()) {
            urlList.add(matcher_a.group(1));
//            for (int i=0;i<= matcher_a.groupCount();i++){
//                System.out.println(i+":"+matcher_a.group(i));
//            }
        }
        List<VideoDTO> videoDTOList = new ArrayList<>();
        //获取影片名和下载地址
        for (String infoUrl : urlList) {
            infoUrl = hostUrl +infoUrl;
            videoDTOList.addAll(getDownloadUrl(infoUrl));
//            for (int i=0;i<= matcher_a.groupCount();i++){
//                System.out.println(i+":"+matcher_a.group(i));
//            }
        }

        return videoDTOList;
    }

    public static void main(String[] args) throws Exception {
//        List<VideoDTO> videoDTOList = getDownloadUrl("http://www.dy2046.net/aiqing/taiyangyeshixingxing/#down");
        List<VideoDTO> videoDTOList = getDownloadUrlByPage("http://www.dy2046.net/aiqing/index9.html");
        for (VideoDTO videoDTO : videoDTOList) {
            System.out.println(videoDTO.name+"-"+videoDTO.movieType +"-"+videoDTO.passcodeType);
        }
    }
}
