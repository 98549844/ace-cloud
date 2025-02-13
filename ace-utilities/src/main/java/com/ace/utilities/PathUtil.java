package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Arrays;

/**
 * @Classname: PathUtil
 * @Date: 11/7/2021 3:06 上午
 * @Author: garlam
 * @Description:
 */


public class PathUtil {
    private static final Logger log = LogManager.getLogger(PathUtil.class.getName());

    public static final String separator = File.separator;


    /**
     * 处理路径内有空格问题
     *
     * @param path
     * @return
     */
    public static String space(String path) {
        return path.trim().replaceAll(" ", "%20");
    }


    public static void main(String[] args) throws IOException {
        //  String p = "C:\\Users\\Garlam.Au\\IdeaProjects\\ace-cloud\\ace-utilities\\src\\main\\resources\\ace-util.txt";
        PathUtil pathUtil = new PathUtil();
        System.out.println("getSrcFile");
        PathUtil.getSrcFile("src\\main\\resources\\banner.txt");

        System.out.println("getResourceContent");
        pathUtil.getResourceContent("/banner.txt");

        System.out.println("getResourcePath");
        pathUtil.getResourcePath("\\banner.txt");

        System.out.println("getSystemPath");
        System.out.println(PathUtil.getSystemPath());

        System.out.println("getClassLoaderAbsolutePath");
        System.out.println(PathUtil.getClassLoaderAbsolutePath());

        PathUtil.printBaseUrl();
    }



    public static File getSrcFile(String srcFile) throws IOException {
        log.info("format: src/main/java/com/.../xx.xx");
        File file = new File(srcFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }
        return file;
    }

    public static File getSourceFile(String srcFile) throws IOException {
        log.info("format: src/main/resources/.../xx.xx");
        File file = new File(srcFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }
        return file;
    }

    /**
     * 读取resource文内件容
     *
     * @param resource
     * @return
     * @throws IOException
     */
    public File getResourceContent(String resource) throws IOException {
        log.info("read file location: ace/src/main/resources");
        log.info("format: /../../xxx.xx");
        File file = new File(PathUtil.class.getResource(resource).getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }
        return file;
    }


    public static String getSystemPath() {
        return System.getProperty("user.dir");
    }

    public static String newLine() {
        return System.lineSeparator();
    }

    public static String tab() {
        return "\t";
    }

    public static String getSystemPath(String empty) throws IOException {
        if (NullUtil.nonNull(empty)) {
            empty = "";
        }
        File directory = new File(empty);//参数为空
        return directory.getCanonicalPath();
    }

    /**
     * 返回resource下的文件的路径
     *
     * @param resource
     * @return
     */
    public  String getResourcePath(String resource) {
        // 直接folder名, 不用resource开头
        // 不能直接在main方法运行
        log.info("directly access resource{} ", resource);
        URL url = this.getClass().getResource("/" + resource);
        return new File(url.getFile()).getAbsolutePath();
    }

    /**
     * @return 编辑后java class位置
     */
    public static String getClassLoaderAbsolutePath() {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        return String.valueOf(ClassLoader.getSystemResource(""));
    }

    public static String getBaseUrl(String... locations) throws MalformedURLException {
        String baseUrl = FileSystems
                .getDefault()
                //  .getPath("src", "main", "resources", "templates", "ace", "modules", "reports", "pdf")
                .getPath(Arrays.toString(locations))
                .toUri()
                .toURL()
                .toString();
        System.out.println("baseUrl: " + baseUrl);
        return baseUrl;
    }

    public static String printBaseUrl() throws MalformedURLException {
        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources", "templates", "ace", "modules", "reports", "pdf")
                .toUri()
                .toURL()
                .toString();
        System.out.println("baseUrl: " + baseUrl);
        return baseUrl;
    }

}
