package com.itstyle.modules.wechatpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author Lipanye_Arthur@163.com
 * @Date 2020/04/07 13:22
 * @Decription http请求(这里用户获取订单url生成二维码)
 */
public class HttpUtil {
    private final static int CONNECT_TIMEOUT=5000;
    private final static String DEFAULT_ENCODING="UTF-8";


    public static String postData(String urlStr, String data) {
        return postData(urlStr,data,null);
    }

    private static String postData(String urlStr, String data, String contentType) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(CONNECT_TIMEOUT);
            if(contentType!=null){
                connection.setRequestProperty("content-type",contentType);
            }
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),DEFAULT_ENCODING);
            if(data == null){
                data = "";
            }
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line=reader.readLine())!=null){
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
