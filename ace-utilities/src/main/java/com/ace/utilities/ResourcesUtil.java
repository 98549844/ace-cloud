package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Classname: PropertyUtil
 * @Date: 2022/7/28 下午 02:40
 * @Author: kalam_au
 * @Description:
 */

public class ResourcesUtil {
    private static final Logger log = LogManager.getLogger(ResourcesUtil.class.getName());

    //location: src/main/resources/properties/messages.properties
    public static String utilitiesProperties = "properties/utilities.properties";
    public static String messagesProperties = "properties/messages.properties";

    public static String getValueByKey(String propertiesPath, String key) throws IOException {
        log.info("print static properties value");
        Properties properties = new Properties();
        InputStream inStream = ResourcesUtil.class.getClassLoader().getResourceAsStream(propertiesPath);
        properties.load(inStream);
        Console.println(properties.get(key).toString(), Console.BOLD, Console.FLUORESCENT_PURPLE);
        return (String) properties.get(key);
    }


    public static void main(String[] args) throws Exception {
        getValueByKey(utilitiesProperties, "version");
        printProperties(messagesProperties);
        System.out.println(get("hbm/Users.hbm.xml"));

    }

    /**
     * 直接读取resources目录内的文件, 不需要以/开头
     *
     * @param propertiesPath
     * @throws IOException
     */
    public static void printProperties(String propertiesPath) throws IOException {
        if (!propertiesPath.contains(".properties")) {
            //只处理properties文件
            log.error("This is not a properties file");
            return;
        }

        log.info("print static properties");
        Properties properties = new Properties();
        InputStream inputStream = ResourcesUtil.class.getClassLoader().getResourceAsStream(propertiesPath);
        properties.load(inputStream);
        for (String key : properties.stringPropertyNames()) {
            Console.println(key + "=" + properties.getProperty(key), Console.BOLD, Console.FLUORESCENT_PURPLE);
        }
    }

    /**
     * 直接读取resources目录内的文件內容, 不需要以/开头
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String get(String filePath) throws IOException {
        InputStream inputStream = ResourcesUtil.class.getResourceAsStream(FileUtil.separator + filePath);
        StringBuilder sb = new StringBuilder();

        if (inputStream != null) {
            // 在这里处理文件的输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(SystemUtil.newLine());
            }
            reader.close();
        } else {
            String errorMsg = "读取文件失败, 请检查文件路径是否正确";
            log.error(errorMsg);
            sb.append(errorMsg);
        }
        return sb.toString();
    }

}
