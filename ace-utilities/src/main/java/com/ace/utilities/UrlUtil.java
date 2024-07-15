package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @Classname: UrlUtil
 * @Date: 29/2/2024 10:41 am
 * @Author: garlam
 * @Description:
 */


public class UrlUtil {
    private static final Logger log = LogManager.getLogger(UrlUtil.class.getName());
    private static final String sampleUrl = "https://www.apache.org/licenses/LICENSE-2.0.txt";

    public static void main(String[] args) {
        String target = "https://www.bilibili.com/";
        System.out.println(getUrlConnectionStatus(target));
        readFileContentByUrl(sampleUrl);
    }

    /**
     * 检查url是否可连接
     *
     * @param url
     * @return
     */
    public static boolean getUrlConnectionStatus(String url) {
        System.out.println("url: " + url);
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.connect();
            System.out.println("connection is ok");
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("connection is fail");
            return false;
        }
    }


    /** 讀取指向url的文本或html
     * @param link
     */
    public static void readFileContentByUrl(String link) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理读取到的数据
                System.out.println(line);
            }
            reader.close();
            inputStream.close();
            // connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileContentByUrl(String link) {
        String res = null;
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            res = readInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从输入流中获取字符串
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toString(StandardCharsets.UTF_8);

    }
}

