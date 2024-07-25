package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


/**
 * @Classname: SerializableUtil
 * @Date: 23/7/24 PM11:28
 * @Author: garlam
 * @Description:
 */

//https://blog.csdn.net/u011019141/article/details/114313447 for 文件
//https://blog.51cto.com/u_16213304/10757098 for 数据库
public class SerializableUtil {
    private static final Logger log = LogManager.getLogger(SerializableUtil.class.getName());

    /**
     * 序列化到文件
     *
     * @param file
     * @param object
     */
    public static void serializeToFile(String file, Object object) {
        try {
            FileOutputStream fos = new FileOutputStream(file); //文件不存在时自动
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            ioException.printStackTrace();
        }
    }

    /**
     * 文件内容反序列化
     *
     * @param file
     */
    public static <T> T deserializationFromFile(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 序列化到byte[], 用作存入数据库
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        byte[] objectBytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            objectBytes = baos.toByteArray();
            oos.flush();
            oos.close();
            return objectBytes;
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            ioException.printStackTrace();
        }
        return objectBytes;
    }

    /**
     * byte[]内容反序列化, 用作数据反序列
     *
     */
    public static <T> T deserialize(byte[] objectBytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}

