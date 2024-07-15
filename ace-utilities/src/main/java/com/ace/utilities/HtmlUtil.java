package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.tidy.Tidy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import static com.ace.constants.constant.UTF_8;

/**
 * @Classname: HtmlUtil
 * @Date: 2023/9/26 上午 09:52
 * @Author: kalam_au
 * @Description:
 */


public class HtmlUtil {
    private static final Logger log = LogManager.getLogger(HtmlUtil.class.getName());


    /** 转成xhtml并检查语法
     * @param renderHtmlContent
     * @return
     */
    public String convertToXHtml(String renderHtmlContent) {
        log.info("convert to xhtml......!");
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(renderHtmlContent.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    /**
     * 根據URL獲取HTML內容
     *
     * @param targetUrl
     * @return
     */
    public String getHtmlFromUrl(String targetUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(targetUrl);
            URLConnection URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}

