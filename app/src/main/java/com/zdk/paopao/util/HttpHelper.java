package com.zdk.paopao.util;

import android.annotation.SuppressLint;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/26.
 */

public class HttpHelper {
    @SuppressLint("DefaultLocale")
    public static String get_Http_Post(String urladdress,String write_data) {

        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(urladdress);
            httpConn = (HttpURLConnection) url.openConnection();
            // //设置连接属性
            httpConn.setDoOutput(true);// 使用 URL 连接进行输出
            httpConn.setDoInput(true);// 使用 URL 连接进行输入
            httpConn.setUseCaches(true);// 忽略缓存
//       User-Agent:
            //zpp版  传入  seller
            httpConn.setRequestProperty("User-Agent", "Myncic/1.0(seller)");//这个方法是设置http请求的头。具体的需要查看HTTP请求参数设置
            httpConn.setRequestMethod("POST");// 设置URL请求方法
            httpConn.setConnectTimeout(10000);
            //数据写入：
            // 准备数据
            byte[] data_bytes = write_data.getBytes();
            httpConn.setRequestProperty("Content-Length", data_bytes.length + "");
            // POST方式：浏览器将数据以流的方式写入服务器
            httpConn.setDoOutput(true);// 允许向外部写入数据
            OutputStream os = httpConn.getOutputStream();
            os.write(data_bytes);
            int responseCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
                InputStream ins=httpConn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] b= new byte[1024];
                int length=-1;
                while ((length = ins.read(b)) !=-1) {
                    baos.write(b, 0, length);
                }
                String data=new String(baos.toByteArray(),"UTF-8");
                httpConn.disconnect();
                return data;
            }else {
                httpConn.disconnect();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
