package com.iwei.wxmanagement.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

@Data
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "wx.test")
public class wxInfo {

    // 获取access_token的接口地址
    public String access_token_url;

    public String appID;

    public String appSecret;

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr){
        JSONObject jsonObject=null;
        StringBuffer buffer=new StringBuffer();
        try{
            //创建SSLcontext管理器对像，使用我们指定的信任管理器初始化
//            TrustManager[] tm={new MyX509TrustManager()};
//            SSLContext sslContext=SSLContext.getInstance("SSL","SunJSSE");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            SSLSocketFactory ssf=sslContext.getSocketFactory();

            URL url= new URL(requestUrl);
            HttpsURLConnection httpsUrlConn=(HttpsURLConnection)url.openConnection();
//            httpsUrlConn.setSSLSocketFactory(ssf);
            httpsUrlConn.setDoInput(true);
            httpsUrlConn.setDoOutput(true);
            httpsUrlConn.setUseCaches(false);
            //设置请求方式（GET/POST）
            httpsUrlConn.setRequestMethod(requestMethod);
            // if("GET".equalsIgnoreCase(requestMethod)){
            httpsUrlConn.connect();
            //  }

            //当有数据需要提交时
            if(outputStr!=null){
                OutputStream outputStream=httpsUrlConn.getOutputStream();
                //防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
                outputStream=null;
            }

            //将返回的输入流转换成字符串
            InputStream inputStream=httpsUrlConn.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            String str=null;
            while((str=bufferedReader.readLine())!=null){
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream=null;

            httpsUrlConn.disconnect();
            jsonObject=JSONObject.parseObject(buffer.toString());
            // System.out.println(jsonObject);

        }
        catch (ConnectException ce) {
            // TODO: handle exception
            ce.printStackTrace();
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return jsonObject;
    }
}
