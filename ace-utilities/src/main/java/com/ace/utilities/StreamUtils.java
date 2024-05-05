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


public class StreamUtils {
    private static final Logger log = LogManager.getLogger(StreamUtils.class.getName());
    public static void main(String[] args) {
        System.out.println("Hello world!");

        InputStream inputStream = null;;
        ByteArrayInputStream inputStream2 = null;;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        ObjectInputStream objectInputStream = null;
        DataInputStream dataInputStream = null;

        OutputStream outputStream = null;
        ByteArrayInputStream outputStream2 = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        DataOutputStream dataOutputStream = null;

    }

}

