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
//            System.out.println(matcher_a.group(1));
            downloadUrl.add(matcher_a.group(1));
        }


        if (downloadUrl.size() > 2) {
            proInfo = Pattern.compile("class=\"down_url\" value=\"(.*?)\" file_name=\"(.*?)\"");
            matcher_a = proInfo.matcher(downloadUrl.get(2));
            while (matcher_a.find()) {
                VideoDTO videoDTO = new VideoDTO();
                videoDTO.name = matcher_a.group(2).replace("[迅雷下载www.DY2046.Net]", "");
                videoDTO.url = matcher_a.group(1);
                videoDTOList.add(videoDTO);
            }
        } else {
            for (String downUrl : downloadUrl) {
                proInfo = Pattern.compile("class=\"down_url\" value=\"(.*?)\" file_name=\"(.*?)\"");
                matcher_a = proInfo.matcher(downUrl);
                while (matcher_a.find()) {
                    VideoDTO videoDTO = new VideoDTO();
                    videoDTO.name = matcher_a.group(2).replace("[迅雷下载www.DY2046.Net]", "");
                    videoDTO.url = matcher_a.group(1);
                    videoDTOList.add(videoDTO);
                }
            }
        }

        return videoDTOList;

    }

    //获取电影名
    public static String getMovieInfo(String url) throws Exception {
        String str = getURLInfo(url);

        Pattern proInfo = Pattern.compile("<div class=\"detail-title fn-left\">.*?<h2>(.*?)迅雷下载</h2>");
        Matcher matcher_a = proInfo.matcher(str);
        String name = "";

        while (matcher_a.find()) {
            name = matcher_a.group(1);
        }

        return name;

    }

    public static void main(String[] args) throws Exception {
        List<VideoDTO> videoDTOList = getDownloadUrl("http://www.dy2046.net/dalu/tingxuelou/");
        for (VideoDTO videoDTO : videoDTOList) {
            System.out.println(videoDTO.name +":"+videoDTO.url);
        }
    }
}
