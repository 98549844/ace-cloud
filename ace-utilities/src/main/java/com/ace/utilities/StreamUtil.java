package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


/**
 * @Classname: StreamUtils
 * @Date: 5/5/24 AM10:42
 * @Author: garlam
 * @Description:
 */


public class StreamUtil {
    private static final Logger log = LogManager.getLogger(StreamUtil.class.getName());

    public static void main(String[] args) throws IOException {
        //InputStream inputStream = getInputStream("log4j2.xml");
        //ByteArrayInputStream inputStream2 = null;;
        //FileInputStream fileInputStream = null;
        //BufferedInputStream bufferedInputStream = null;
        //ObjectInputStream objectInputStream = null;
        //DataInputStream dataInputStream = null;
        //
        //OutputStream outputStream = null;
        //ByteArrayInputStream outputStream2 = null;
        //FileOutputStream fileOutputStream = null;
        //BufferedOutputStream bufferedOutputStream = null;
        //ObjectOutputStream objectOutputStream = null;
        //DataOutputStream dataOutputStream = null;

        StreamUtil streamUtil = new StreamUtil();
        System.out.println(streamUtil.getFileInputStream("/Users/garlam/IdeaProjects/ace-cloud/ace-utilities/src/main/resources/log4j2.xml"));


    }

    /**
     * 直接读取resources目录内的文件, 不需要以/开头
     *
     * @param filePath
     * @return InputStream
     */
    public InputStream getInputStream(String filePath) {
        return StreamUtil.class.getResourceAsStream(FileUtil.separator + filePath);
    }

    /**
     * InputStream缓存到ram
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public ByteArrayOutputStream getByteArrayOutputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        if (inputStream != null) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        } else {
            String errorMsg = "读取文件失败, 请检查文件路径是否正确";
            log.error(errorMsg);
        }
        return baos;
    }

    public FileInputStream getFileInputStream(String absolutePath) {
        try {
            return new FileInputStream(absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 直接读取resources目录内的文件, 不需要以/开头
     *
     * @param filePath
     * @return String
     */
    public String getString(String filePath) throws IOException {
        InputStream inputStream = getInputStream(filePath);
        StringBuilder sb = new StringBuilder();
        if (inputStream != null) {
            // 在这里处理文件的输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(SystemUtil.newLine());
            }
            reader.close();
            inputStream.close();
        } else {
            String errorMsg = "读取文件失败, 请检查文件路径是否正确";
            log.error(errorMsg);
            sb.append(errorMsg);
        }
        return sb.toString();
    }

}

