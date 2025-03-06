package com.ace.utilities;

import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.K;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unchecked")
public class FileUtil {
    private static final Logger log = LogManager.getLogger(FileUtil.class.getName());

    public static final String FOLDER_LIST = "folderList";
    public static final String FOLDER_NAME = "folderName";
    public static final String FILE_LIST = "fileList";
    public static final String ORIGINAL = "original"; //原文
    public static final String LINE = "line"; //压缩成一行
    public static final String LIST = "list";
    public static final String STRING = "string";
    public static final String MAP = "map";
    public static final String SET = "set";

    public static final String PATH = "path";
    public static final String FILENAME = "fileName";
    public static final String EXT = "ext";
    public static final String FILENAME_WITH_EXT = "fileNameWithExt";
    public static final String separator = File.separator; // 系统路径分隔符 相当于"/"

    private static final String PREFIX_VIDEO = "video/";
    private static final String PREFIX_IMAGE = "image/";
    private static final String PREFIX_APPLICATION = "application/";


    /**
     * 系统路径分隔符 相当于"/"
     */
    public static String separator() {
        return File.separator;
    }

    public static String tab() {
        return "\t";
    }

    /**
     * 通用换行符
     */
    public static String newLine() {
        return System.lineSeparator();
    }

    /**
     * 转半角的函数(DBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input 任意字符串由全角转半角
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是:均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    public static boolean exist(String path) {
        return new File(path).exists();
    }

    /**
     * 创建文件
     *
     * @return
     */
    public static boolean create(String path) {
        File file = new File(path);
        boolean result = false;
        try {
            if (!file.exists()) {
                FileUtil.mkDirs(path);
                result = file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, List<String>> getCurrentFolderList(String path) throws Exception {
        log.info("get current: {}", path);

        File file = new File(path);
        Map<String, List<String>> map = new HashMap<>();
        if (!file.exists() || !file.isDirectory()) {
            log.warn("not directory or not exist !");
            return map;
        }
        File[] files = file.listFiles();
        List<String> fullFolderList = new ArrayList<>();
        List<String> folderList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    fullFolderList.add(f.getAbsolutePath() + File.separator);
                    folderList.add(f.getName());
                } else if (f.isFile()) {
                    fileList.add(f.getAbsolutePath());
                }
            }
        } else {
            throw new Exception("File list is null!");
        }
        map.put(FOLDER_LIST, fullFolderList);
        map.put(FOLDER_NAME, folderList);
        map.put(FILE_LIST, fileList);
        return map;
    }

    /**
     * 文件转为二进制数组
     *
     * @param p
     * @return
     */
    public static byte[] toByte(String p) {
        File file = new File(p);
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return bytes;
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array error: ", ex);
        }
    }

    /**
     * 文件转为二进制字符串
     *
     * @param p
     * @return
     */
    public static String toByteString(String p) {
        File file = new File(p);
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin String error: ", ex);
        }
    }


    /**
     * get current folder and subfolder
     *
     * @param path
     * @return
     */
    public static LinkedList<String> getFolderAndSubFolderList(String path) {
        LinkedList<String> list = new LinkedList<>();
        path = getParent(path);
        //考虑到会打成jar包发布 new File()不能使用改用FileSystemResource
        File file = new FileSystemResource(path).getFile();
        // 获取路径下的所有文件及文件夹
        File[] files = file.listFiles();
        for (File value : files) {
            if (value.isDirectory()) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                //add dir into list
                list.add(value.getPath());
                list.addAll(getFolderAndSubFolderList(value.getPath()));
            }
        }
        return list;
    }

    public static void copy(String source, String dest) throws IOException {
        File src = new File(source);
        File desc = new File(dest);
        if (NullUtil.isNull(source) || !src.isFile()) {
            log.error("error! source: {}", source);
        }
        FileUtils.copyFile(src, desc);
    }

    /**
     * 批量重复制并重命名后缀
     * 用缓冲区读写，来提升读写效率。
     */
    public static void copyFileWithNewExt(String path, List<String> fileNames, String ext, boolean delFile) {
        if (!exist(path)) {
            log.error("Directory not exist or incorrect !!!");
            return;
        }

        path = getParent(path);
        log.info("Start copying file ...");
        for (String fileName : fileNames) {
            FileWriter fw = null;
            FileReader fr = null;
            String newFileName;
            if (!fileName.substring(fileName.length() - 4).equals(ext)) {
                newFileName = fileName.substring(0, fileName.length() - 4) + ext;
            } else {
                newFileName = "checked_" + fileName;
            }
            try {
                fr = new FileReader(path + fileName);//读
                fw = new FileWriter(path + newFileName);//写
                char[] buf = new char[1024];//缓冲区
                int len;
                while ((len = fr.read(buf)) != -1) {
                    fw.write(buf, 0, len);//读几个写几个
                }
                File file = new File(path + fileName);
                if (delFile && file.exists()) {
                    file.delete();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                if (NullUtil.nonNull(fr)) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (NullUtil.nonNull(fw)) {
                    try {
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        log.info("File writing complete !");
    }


    public static void renameFilesName(String path, List<String> newNameList) throws Exception {
        log.info("Start rename file");
        path = getParent(path);
        List<String> fileList = FileUtil.getFileNamesWithExt(path);
        if (fileList.size() != newNameList.size()) {
            log.error("List size not equal");
            return;
        }
        for (int i = 0; i < newNameList.size(); i++) {
            File oldFile = new File(path + fileList.get(i));
            File newFile = new File(path + newNameList.get(i));
            if (newFile.exists()) {
                newFile = new File(path + "_" + newNameList.get(i));
            }
            oldFile.renameTo(newFile);
        }
        log.info("Finish rename file");
    }

    public static void rename(String src, String desc) throws Exception {
        log.info("Start rename file");
        // 旧的文件或目录
        File oldName = new File(src);
        // 新的文件或目录
        File newName = new File(desc);
        if (newName.exists()) {  //  确保新的文件名不存在
            throw new IOException("target file exists!");
        }
        boolean success = oldName.renameTo(newName);
        if (success) {
            log.info("File renamed success => {}", desc);
        } else {
            if (src.equals(desc)) {
                log.error("New file name same with original!");
            }
            log.error("File rename fail!");
        }
    }


    private static String extension(String ext) {
        if (NullUtil.isNull(ext)) {
            throw new NullPointerException("extension is null");
        } else {
            if (ext.startsWith(".")) {
                return ext;
            } else {
                return "." + ext;
            }
        }
    }

    public static void renameFilesExt(String path, String newExt) throws Exception {
        log.info("Start rename ext");
        path = getParent(path);
        List<String> fileList = FileUtil.getFileNamesWithExt(path);
        int i = 1;
        for (String s : fileList) {
            String[] spiltFileName = Strings.split(s, ".");
            newExt = extension(newExt);
            String filePath = path + s;
            File oldFile = new File(filePath);
            File newFile = new File(path + spiltFileName[0] + newExt);
            if (newFile.exists()) {
                ++i;
                String t = "_" + i;
                newFile = new File(path + spiltFileName[0] + t + newExt);
            }
            boolean isSuccess = oldFile.renameTo(newFile);
            if (!isSuccess) {
                log.info("rename fail: {}", oldFile);
            }
        }
        log.info("Rename success, total renamed files: {}", fileList.size());
    }


    /**
     * 根据file获取文件路径
     *
     * @param file
     * @return
     */
    public static String getParent(String file) {
        if (isDir(file)) {
            return file;
        }
        return new File(file).getParent()+File.separator;
    }

    /**
     * get list of xxx.xx
     *
     * @param path
     * @return
     */
    public static List<String> getFileNamesWithExt(String path) {
        path = getParent(path);
        log.info("Folder: {}", path);
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] fileLists = file.listFiles();
        assert fileLists != null;
        for (File f : fileLists) {
            if (f.isFile() && !f.getName().equals(".DS_Store")) {
                files.add(f.getName());//file name
            }
        }
        return files;
    }

    /**
     * get map key 和 value 都是file name
     *
     * @param path
     * @return
     */
    public static Map<String, String> getFileNamesWithExtMap(String path) {
        path = getParent(path);
        log.info("Folder: {}", path);
        Map<String, String> files = new HashMap<>();
        File file = new File(path);
        File[] fileLists = file.listFiles();
        for (File f : fileLists) {
            if (f.isFile() && !f.getName().equals(".DS_Store")) {
                files.put(f.getName(), f.getName());//file name
            }
        }
        return files;
    }

    /**
     * 绝对路经 xxx/xxx/xxx/xxx.xx
     *
     * @param path
     * @return xxx.xx
     * @throws IOException
     */
    public static String getNameWithExt(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            throw new IOException("Is directory!");
        } else if (!file.exists()) {
            file.createNewFile();
        }
        return file.getName();
    }


    /**
     * list 绝对路经 xxx/xxx/xxx/xxx.xx
     *
     * @param path
     * @return
     */
    public static ArrayList<String> getCurrentFolderAbsoluteFilesPath(String path) {
        path = getParent(path);
        log.info("Folder: {}", path);

        File file = new File(path);
        File[] tempLists = file.listFiles();
        ArrayList<String> files = new ArrayList<>();
        for (File tempList : tempLists) {
            if (tempList.isFile() && !tempList.getName().equals(".DS_Store")) {
                files.add(tempList.getAbsolutePath());//full path
            }
        }
        return files;
    }


    public static String getContent(String src) throws IOException {
        File file = new File(src); // 要读取以上路径的文本内容
        log.info("file name: {}", file.getName());
        byte[] bytes = new byte[1024];
        StringBuffer sb = new StringBuffer();
        FileInputStream input = new FileInputStream(file);
        int len;
        while ((len = input.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len));
        }
        System.out.println(sb);
        input.close();
        return sb.toString();
    }


    public static Map<String, Object> read(String path) throws IOException {
        if (!isFile(path)) {
            throw new IOException("can't read this file, please check !");
        }
        File f = new File(path);
        // 建立一个输入流对象reader
        InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
        // 建立一个对象，它把文件内容转成计算机能读懂的语言
        BufferedReader br = new BufferedReader(reader);
        String line;
        line = br.readLine();
        int i = 1;

        StringBuilder content1 = new StringBuilder();
        StringBuilder content2 = new StringBuilder();
        List<String> content3 = new LinkedList<>();
        while (NullUtil.nonNull(line) || "".equals(line)) {
            // 一次读入一行数据,并显示行数
            // content1.append(i + ". ");
            content1.append(line.trim()).append(SystemUtil.newLine());
            content2.append(line.trim());
            content3.add(line.trim());
            i++;
            // 把所有内容在一行显示
            line = br.readLine();
        }
        br.close();
        reader.close();

        Map<String, Object> map = new HashMap<>();
        map.put(FileUtil.ORIGINAL, content1.toString()); // 返回原文
        map.put(FileUtil.LINE, content2.toString()); // 返回一行
        map.put(FileUtil.LIST, content3); // 返回一个list
        return map;
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    public static boolean isDir(String path) {
        return new File(path).isDirectory();
    }


    /**
     * accord path to get contented file name
     *
     * @param path
     * @return
     */
    public static Map<String, List<String>> getFileNamesMap(String path) {
        List<String> ls = getFolderAndSubFolderList(path);
        Map<String, List<String>> result = new HashMap<>();
        for (String folder : ls) {
            List<String> files = getFileNamesWithExt(folder);
            result.put(folder, files);
        }
        return result;
    }


    public static Map<Object, Object> getFullPathDirTree(String path) {
        Map<Object, Object> m = new LinkedHashMap<>();
        path = getParent(path);
        File file = new FileSystemResource(path).getFile();
        // 获取路径下的所有文件及文件夹
        File[] files = file.listFiles();
        int size = files.length;
        for (int i = 0; i < size; i++) {
            if (files[i].isDirectory()) {
                String folder = files[i].getPath();
                Map<Object, Object> t = getFullPathDirTree(folder);
                m.put(folder, t);
            } else {
                String fPath = files[i].getPath();
                m.put(i, fPath);
            }
        }
        return m;
    }

    /**
     * 获取路径下所有文件夹
     *
     * @param path
     * @return
     */
    public static LinkedList<String> getFullPathDirList(String path) {
        LinkedList<String> list = new LinkedList<>();
        path = getParent(path);
        //考虑到会打成jar包发布 new File()不能使用改用FileSystemResource
        File file = new FileSystemResource(path).getFile();
        // 获取路径下的所有文件及文件夹
        File[] files = file.listFiles();
        for (File value : files) {
            if (value.isDirectory()) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                //add dir into list
                list.add(value.getPath());
                list.addAll(getFullPathDirList(value.getPath()));
            } else {
                list.add(value.getPath());
            }
        }
        return list;
    }

    /**
     * 删除line 0 到 lineNum的内容
     * 主要用于log太大, 把多余的log删除
     *
     * @param fullPath
     * @param lineNum
     * @return
     * @throws IOException
     */
    public static List<String> removeLinesByLineNum(String fullPath, int lineNum) throws IOException {
        File file = new File(fullPath);
        List<String> strList = new ArrayList<String>();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            //Initial write position
            long writePosition = raf.getFilePointer();
            for (int i = 0; i < lineNum; i++) {
                String line = raf.readLine();
                if (NullUtil.isNull(line)) {
                    break;
                }
                strList.add(line);
            }
            // Shift the next lines upwards.
            long readPosition = raf.getFilePointer();
            byte[] buff = new byte[1024];
            int n;
            while (-1 != (n = raf.read(buff))) {
                raf.seek(writePosition);
                raf.write(buff, 0, n);
                readPosition += n;
                writePosition += n;
                raf.seek(readPosition);
            }
            raf.setLength(writePosition);
        } catch (IOException e) {
            log.error("Remove Lines error", e);
            throw e;
        } finally {
            try {
                if (NullUtil.nonNull(raf)) {
                    raf.close();
                }
            } catch (IOException e) {
                log.error("close Random Access File error", e);
                throw e;
            }
        }
        return strList;
    }

    /**
     * folder not exist, create folders
     *
     * @param path
     */
    public static void mkDirs(String path) {
        File f = new File(path);
        if (!f.exists()) {
            if (path.lastIndexOf(".") == -1) { //检查路径不包含文件名
                f.mkdirs();
            } else {
                new File(f.getParent()).mkdirs();
            }
        }
    }


    /**
     * 檢查文件是否可寫
     *
     * @param path
     * @return
     */
    public static boolean canWrite(String path) {
        return new File(path).canWrite();
    }

    public static boolean setWritable(String path, boolean writable) {
        File file = new File(path);
        return file.setWritable(writable);
    }

    /**
     * 檢查文件是否被其他線程佔用
     *
     * @param path
     * @return
     */
    public static boolean isUsed(String path) {
        try (RandomAccessFile file = new RandomAccessFile(path, "rw"); FileChannel channel = file.getChannel()) {
            FileLock lock = channel.tryLock();
            if (lock == null) {
                log.error("File used by other !");
                return true; // 文件被占用
            }
            lock.release();
            return false; // 文件未被占用
        } catch (Exception e) {
            e.printStackTrace();
            return true; // 发生异常，视为文件被占用
        }
    }


    /**
     * 清空原文并写入新内容
     */
    public static void write(String path, Object object, boolean append) throws IOException {
        String filePath = getParent(path) + FileUtil.separator();
        String fileName = getNameWithExt(path);
        write(filePath, fileName, object, append);
    }

    /**
     * @param filePath
     * @param fileName
     * @param obj
     * @param append   true为接着原文写入，false为覆盖原文
     */
    public static void write(String filePath, String fileName, Object obj, boolean append) {
        if (NullUtil.isNull(obj)) {
            throw new NullPointerException("Object is null !");
        }
        create(filePath + fileName);
        //boolean isOk = false;
        String type;
        StringBuilder content = null;
        Set<?> contentSet = null;
        List<String> contentList = new ArrayList<>();
        Map<?, ?> contentMap = new HashMap<>();
        if (obj instanceof String) {
            content = new StringBuilder((String) obj);
            type = STRING;
        } else if (obj instanceof List) {
            contentList = (List<String>) obj;
            type = LIST;
        } else if (obj instanceof Map<?, ?>) {
            contentMap = (Map<?, ?>) obj;
            type = MAP;
        } else if (obj instanceof Set<?>) {
            contentSet = (Set<?>) obj;
            type = SET;
        } else {
            log.error("UN-DEFAULT TYPE!");
            return;
        }

        FileOutputStream fop = null;
        try {
            File file = new File(filePath + fileName);
            if (append) {
                fop = new FileOutputStream(file, true);
            } else {
                fop = new FileOutputStream(file);
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fop, StandardCharsets.UTF_8);

            // get the content in bytes
            String contentInBytes = null;
            switch (type) {
                case STRING -> {
                    contentInBytes = content.toString();
                    outputStreamWriter.append(contentInBytes);
                    outputStreamWriter.flush();
                }
                case LIST -> {
                    for (String s : contentList) {
                        // contentInBytes = contentList.get(i).toString().getBytes();
                        // fop.write(contentInBytes);
                        contentInBytes = s;
                        outputStreamWriter.append(contentInBytes);
                        outputStreamWriter.flush();
                    }
                }
                case MAP -> {
                    //寫成一pet野甘, 有時間優化
                    contentInBytes = contentMap.toString();
                    outputStreamWriter.append(contentInBytes);
                    outputStreamWriter.flush();
                }
                case SET -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("[").append(SystemUtil.newLine());
                    for (Object s : contentSet) {
                        sb.append(s.toString()).append(",").append(SystemUtil.newLine());
                    }
                    sb.append("]");
                    outputStreamWriter.append(sb.toString());
                    outputStreamWriter.flush();
                }
                default -> log.error("contentInBytes: {}", contentInBytes);
            }
            outputStreamWriter.close();
            // fop.flush();
            // fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (NullUtil.nonNull(fop)) {
                    fop.flush();
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //check file and dir status
    public static void fileStatus(String path, String fileName) {
        path = getParent(path);
        //check dir exist
        File folder = new File(path);
        if (!folder.exists()) {
            System.out.print("Path not exist!");
        }
        if (folder.isDirectory()) {
            System.out.print("Path is not directory!");
        }
        //check file exist
        File file = new File(path + fileName);
        if (!file.exists()) {
            System.out.println("file not exist!");
        } else {
            if (file.exists() && file.length() == 0) {
                System.out.print("File is empty. ");
            }
        }
    }

    /**
     * 读取文件并返回encoding
     *
     * @param f
     * @return
     */
    public static String getEncoding(String f) {
        File file = new File(f);
        String ENCODING = "GBK";
        if (!file.exists()) {
            log.error("get encoding: file not exists !");
            return ENCODING;
        }
        byte[] buf = new byte[4096];
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            // (1)
            UniversalDetector detector = new UniversalDetector(null);
            // (2)
            int nRead;
            while ((nRead = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nRead);
            }
            // (3)
            detector.dataEnd();
            // (4)
            String encoding = detector.getDetectedCharset();
            if (NullUtil.nonNull(encoding)) {
                log.info("Detected encoding = " + encoding);
            } else {
                log.info("No encoding detected.");
            }
            // (5)
            detector.reset();
            fis.close();
            if (NullUtil.isNull(encoding) || encoding.equals("IBM855") || encoding.equals("WINDOWS-1252"))
                encoding = ENCODING;
            return encoding;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("ENCODING: {}", ENCODING);
        return ENCODING;
    }

    public static boolean compareFile(String fileA, String fileB) throws IOException {
        File fileX = new File(fileA);
        File fileY = new File(fileB);
        if (!fileX.isFile() || !fileY.isFile()) {
            throw new IOException("File not exist!");
        }
        String md5X = getMD5(fileX);
        String md5Y = getMD5(fileY);
        if (NullUtil.isNull(md5X, md5Y)) {
            throw new NullPointerException("get MD5 value false!");
        }
        boolean isEquals = md5X.equals(md5Y);
        log.info("Compare result: {}", isEquals);
        return isEquals;
    }

    public static String getMD5(File file) {
        MessageDigest digest;
        FileInputStream in;
        byte[] buffer = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            in.close();
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long getSize(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File not exist");
        }
        DecimalFormat df = new DecimalFormat("#.00");
        if (file.length() / (1024L * 1024L) > 0) {
            double r = (double) file.length() / (1024L * 1024L);
            log.info("{} mb", df.format(r));
        } else if (file.length() / (1024L) > 0) {
            double r = (double) file.length() / (1024L);
            log.info("{} kb", df.format(r));
        } else {
            log.info("{} bytes", file.length());
        }
        return file.length();
    }


    /**
     * 技持完整路径和文件名
     * Used to extract and return the extension of a given file.
     * 後綴不包含 "."
     *
     * @param f Incoming file to get the extension of
     * @return <code>String</code> representing the extension of the incoming
     * file.
     */
    public static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');
        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1);
        }
        return ext;
    }

    public static List<String> getExtensions(List<String> fs) {
        List<String> extensions = new ArrayList<>();
        for (String f : fs) {
            extensions.add(getExtension(f));
        }
        return extensions;
    }


    /**
     * 去除ext
     *
     * @param f fileName.ext
     * @return <code>String</code> representing the filename without its
     * extension.
     */
    public static String getName(String f) {
        File file = new File(f);
        if (file.isFile()) {
            return file.getName().split("\\.")[0];
        } else if (f.contains(".")) {
            return f.split("\\.")[0];
        }
        return f;
    }


    /**
     * 不包括ext
     *
     * @param ls
     * @return
     */
    public static List<String> getNames(List<String> ls) {
        List<String> result = new ArrayList<>();
        if (NullUtil.isNull(ls)) {
            log.warn("List is empty !!!");
            return result;
        }
        for (String s : ls) {
            String[] name = getNameAndExt(s);
            result.add(name[0]);
        }
        return result;
    }
    
    

    public static String[] getNameAndExt(String fileName) {
        File file = new File(fileName);
        String[] result;
        if (file.isFile()) { //如果是文件
            result = file.getName().split("\\.");
        } else {
            result = fileName.split("\\.");
        }
        return result;
    }

    /**
     * @param f 完整路径 xxx/xxx/xxx/xxx.txt
     * @return
     */
    public static Map<String, String> getFileMap(String f) {
        File file = new File(f);
        Map<String, String> map = new HashMap<>();
        String[] fileName = file.getName().split("\\.");
        map.put(FileUtil.PATH, file.getPath());
        map.put(FileUtil.FILENAME_WITH_EXT, file.getName());
        map.put(FileUtil.FILENAME, fileName[0]);
        map.put(FileUtil.EXT, "." + fileName[1]);
        return map;
    }



    /**
     * 完整文件路径, 只用于delete文件
     *
     * @param filePath
     * @return
     */
    public static boolean delete(String filePath) {
        boolean isSuccess = false;
        File f = new File(filePath);
        if (isDir(filePath)) {
            log.warn("The path is a directory, please check !");
            return false;
        }
        if (f.exists()) {
            isSuccess = f.delete();
            log.info("Deleted: {}", f.getAbsolutePath());
        } else {
            log.info("No exist: {}", f.getAbsolutePath());
        }
        return isSuccess;
    }

    /**
     * 删除path下所有文件,但不包括文件夹
     *
     * @param folder
     * @return
     */
    public static boolean deletes(String folder) {
        boolean isSuccess = false;
        List<String> files = getCurrentFolderAbsoluteFilesPath(folder);
        try {
            for (String file : files) {
                File f = new File(file);
                if (f.exists()) {
                    isSuccess = f.delete();
                    log.info("{} File deleted", f.getAbsolutePath());
                } else {
                    log.info("{} File not exist", f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public Map<String, Integer> countByType(String path, String... ext) throws Exception {
        Map<String, Integer> result = new HashMap<>();
        Map<String, Integer> accumulateCount = new HashMap<>();
        countByTypeRecursive(path, accumulateCount);
        if (ext.length == 0) {
            MapUtil.iterateMapKeySet(accumulateCount);
            return accumulateCount;
        } else {
            for (String key : ext) {
                result.putIfAbsent(key, accumulateCount.get(key) == null ? 0 : accumulateCount.get(key));
            }
            MapUtil.iterateMapKeySet(result);
            return result;
        }
    }


    /**
     * count当前文件夹的文件类型
     *
     * @param path 路经 xxx/xxx/xxx/xxx/
     * @throws IOException
     */
    private static void countByTypeRecursive(String path, Map<String, Integer> accumulateCount) throws Exception {
        File folder = new File(path);
        Map<String, Integer> resultMap = new HashMap<>();
        File[] files = folder.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                String type = getExtension(FileUtil.getNameWithExt(f.getAbsolutePath()));
                if (type.isEmpty()) {
                    type = "-";
                }
                int count = resultMap.get(type) == null ? 0 : resultMap.get(type);
                resultMap.put(type, ++count);
            } else {
                countByTypeRecursive(f.getAbsolutePath(), accumulateCount);
            }
        }
        List<String> ls = MapUtil.getKeySet(resultMap);
        System.out.println(folder.getAbsolutePath());
        for (Object object : ls) {
            String key = object.toString();
            int value = resultMap.get(key) == null ? 0 : resultMap.get(object);
            log.info("{} count: {}", key, resultMap.get(key) == null ? 0 : resultMap.get(key));
            Integer newValue = (accumulateCount.get(key) == null ? 0 : accumulateCount.get(key)) + value;
            accumulateCount.put(key, newValue);
        }
    }

    /**
     * count 当前文件夹和文件
     *
     * @param path 绝对路经 xxx/xxx/xxx/xxx.xx
     * @throws IOException
     */
    public static int countCurrent(String path) throws IOException {
        File folder = new File(path);
        int count = 0;
        try {
            if (folder.isFile()) {
                folder = new File(folder.getParent());
            }
            count = Objects.requireNonNull(folder.listFiles()).length;
            log.info("count result: {}", count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 递归count文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static int count(String path) throws IOException {
        File folder = new File(path);
        if (folder.isFile()) {
            folder = new File(folder.getParent());
        }
        FileUtil fileUtil = new FileUtil();
        List<String> ls = fileUtil.getFilePaths(folder.getPath());
        int count = 0;
        for (String object : ls) {
            File temp = new File(object);
            if (temp.isFile()) {
                ++count;
            } else {
                count(temp.getPath());
            }
        }
        log.info("count result: {}", count);
        return count;
    }


    /**
     * @param path 绝对路经 xxx/xxx/xxx/xxx.xx
     * @param ext
     * @throws IOException
     */
    public static Map<String, List<String>> findByType(String path, String... ext) throws Exception {
        FileUtil fileUtil = new FileUtil();
        List<String> fileList = fileUtil.getFilePaths(path);
        log.info("file size: {}", fileList.size());

        Map<String, List<String>> resultMap = new HashMap<>();
        if (NullUtil.nonNull((Object) ext) && ext.length > 0) {
            log.info("starting searching: {}", path);
            for (String f : fileList) {
                String type = getExtension(FileUtil.getNameWithExt(f));
                for (String s : ext) {
                    List<String> resultList = resultMap.get(s) == null ? new ArrayList<>() : resultMap.get(s);
                    if (type.equals(s)) {
                        Console.println(f, Console.BOLD); //print as console
                        resultList.add(f);
                        resultMap.put(s, resultList);
                    }
                }
            }
            log.info("search complete !");
        }
        for (String s : ext) {
            int size = resultMap.get(s) == null ? 0 : resultMap.get(s).size();
            Console.println("type: " + s + " found: " + size, Console.BOLD); //print as console
        }
        return resultMap;
    }

    /**
     * @param path     绝对路经 xxx/xxx/xxx/xxx.xx
     * @param fileName
     * @throws IOException
     */
    public static void findByName(String path, String fileName) throws IOException {
        FileUtil fileUtil = new FileUtil();
        List<String> fileList = fileUtil.getFilePaths(path);
        log.info("starting searching ... {}", path);
        log.info("searching file size: {}", fileList.size());
        for (String f : fileList) {
            String name = getName(FileUtil.getNameWithExt(f));
            if (name.toLowerCase().contains(fileName.toLowerCase())) {
                Console.println(f, Console.BOLD);
            }
        }
        log.info("search complete !");
    }


    private final ArrayList<String> scanFiles = new ArrayList<>();

    /**
     * TODO:递归扫描指定文件夹下面的文件全路径
     *
     * @return ArrayList<Object>
     * @time 2017年11月3日
     */
    public ArrayList<String> getAccumulatedFilesLocation(String folderPath) {
        //在一个instant当中
        //getFilesLocation多次被调用
        //scanFiles会累加之前的结果
        ArrayList<String> directories = new ArrayList<>();
        File directory = new File(folderPath);

        if (directory.isDirectory()) {
            File[] fileList = directory.listFiles();
            /*如果当前是文件夹，进入递归扫描文件夹*/
            /*递归扫描下面的文件夹*/
            /*非文件夹*/
            for (File file : fileList) {
                /*如果当前是文件夹，进入递归扫描文件夹*/
                if (file.isDirectory()) {
                    directories.add(file.getAbsolutePath());
                    /*递归扫描下面的文件夹*/
                    getAccumulatedFilesLocation(file.getAbsolutePath());
                } else {
                    /*非文件夹*/
                    scanFiles.add(file.getAbsolutePath());
                }
            }
        }
        return scanFiles;
    }

    /**
     * 非递归方式扫描指定文件夹下面的所有文件
     *
     * @param folderPath 需要进行文件扫描的文件夹路径
     * @return ArrayList<Object>
     * @time 2017年11月3日
     */
    public List<String> getFilePaths(String folderPath) throws IOException {
        List<String> scanFiles = new ArrayList<>();
        LinkedList<File> queueFiles = new LinkedList<>();
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            throw new IOException("incorrect folder path!");
        } else {
            //首先将第一层目录扫描一遍
            File[] files = directory.listFiles();
            //遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
            for (File file : files) {
                if (file.isDirectory()) {
                    queueFiles.add(file);
                } else {
                    //暂时将文件名放入scanFiles中
                    scanFiles.add(file.getAbsolutePath());
                }
            }
            //如果linkedList非空遍历linkedList
            while (!queueFiles.isEmpty()) {
                //移出linkedList中的第一个
                File headDirectory = queueFiles.removeFirst();
                File[] currentFiles = headDirectory.listFiles();
                for (File currentFile : currentFiles) {
                    if (currentFile.isDirectory()) {
                        //如果仍然是文件夹，将其放入linkedList中
                        queueFiles.add(currentFile);
                    } else {
                        scanFiles.add(currentFile.getAbsolutePath());
                    }
                }
            }
        }
        return scanFiles;
    }


    /**
     * sorting: true=desc; false=asc
     * windows的命名规则是，特殊字符（标点、符号）> 数字 > 字母顺序 > 汉字拼音
     *
     * @param filePath
     */
    public static List<String> getNamesOrderByName(String filePath, boolean sorting) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    if (sorting) {
                        return 1;
                    }
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory()) {
                    if (sorting) {
                        return -1;
                    }
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        List<String> ls = new ArrayList<>();
        for (File f : files) {
            System.out.println(f.getName());
            ls.add(f.getName());
        }
        return ls;
    }


    /**
     * sorting: true=desc; false=asc
     *
     * @param filePath
     * @param sorting
     * @return
     */
//按 文件修改日期: 递增
    public static List<Map<String, String>> getNamesOrderByLastModifiedDate(String filePath, boolean sorting) throws Exception {
        File file = new File(filePath);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0) {
                    if (sorting) {
                        return -1;
                    }
                    return 1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    if (sorting) {
                        return 1;
                    }
                    return -1;
                } //如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }
        });

        List<Map<String, String>> ls = new ArrayList<>();
        // int size = files.length;
        for (File value : files) {
            Map<String, String> map = new HashMap<>();
            String fileName = value.getName();
            map.put(FileUtil.FILENAME, getName(fileName));
            map.put(FileUtil.EXT, getExtension(fileName));
            ls.add(map);
            System.out.print(value.getName() + " => ");
            System.out.println(new Date(value.lastModified()));
        }
        return ls;
    }


    /**
     * sorting: true=desc; false=asc
     *
     * @param filePath 按 文件大小 排序
     */
    public static List<String> getNamesOrderBySize(String filePath, boolean sorting) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<>() {
            public int compare(File f1, File f2) {
                long diff = f1.length() - f2.length();
                if (diff > 0) {
                    if (sorting) {
                        return -1;
                    }
                    return 1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    if (sorting) {
                        return 1;
                    }
                    return -1;
                }
                //如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }
        });

        List<String> ls = new ArrayList<>();
        for (File f : files) {
            if (f.isDirectory()) continue;
            //System.out.println(f.getName() + "    :" + f.length());
            ls.add(f.getName());
        }
        return ls;
    }

    /**
     * File 转 MultipartFile
     *
     * @param file
     * @return
     */
    public static MultipartFile fileToMultipartFile(File file) {
        MultipartFile multipartFile = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile;
    }

    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {

        File file = null;
        //判断是否为null
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            return null;
        }
        //MultipartFile转换为File
        InputStream ins = null;
        OutputStream os = null;
        try {
            ins = multipartFile.getInputStream();
            file = new File(multipartFile.getOriginalFilename());
            os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    // 获取指定路径下的所有文件夹名称
    public static List<String> getDirsName(String path) {
        // 创建File对象，指定路径
        File f = new File(path);
        // 创建List对象，用于存储文件夹名称
        List<String> folderList = new ArrayList<>();
        // 遍历指定路径下的所有文件
        for (File file : f.listFiles()) {
            // 判断文件是否为文件夹
            if (file.isDirectory()) {
                // 将文件夹名称添加到List中
                folderList.add(file.getName());
            }
        }
        // 返回文件夹名称List
        return folderList;
    }

    public static List<String> getCurrentDirs(String path) {
        // 创建一个File对象，表示指定路径的文件或目录
        File f = new File(path);
        // 创建一个List对象，用于存储当前目录下的所有文件夹的绝对路径
        List<String> folderList = new ArrayList();
        // 遍历指定路径下的所有文件和目录
        for (File file : f.listFiles()) {
            // 如果当前文件是一个目录
            if (file.isDirectory()) {
                // 将该目录的绝对路径添加到List中
                folderList.add(file.getAbsolutePath());
            }
        }
        // 返回List对象
        return folderList;
    }


    /**
     * 删除文件或文件夹, 包括自身也会删除
     *
     * @param fileName 文件名
     * @return 删除成功返回true, 失败返回false
     */
    public static boolean deleteDirectories(String fileName) {
        File file = new File(fileName);  // fileName是路径或者file.getPath()获取的文件路径
        if (file.exists()) {
            if (file.isFile()) {
                return deleteFile(fileName);  // 是文件，调用删除文件的方法
            } else {
                return deleteDirectory(fileName);  // 是文件夹，调用删除文件夹的方法
            }
        } else {
            System.out.println("文件或文件夹删除失败：" + fileName);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return 删除成功返回true, 失败返回false
     */
    private static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            System.out.println("删除文件成功：" + fileName);
            return file.delete();
        } else {
            System.out.println("删除文件失败：" + fileName);
            return false;
        }
    }

    /**
     * 删除文件夹
     * 删除文件夹需要把包含的文件及文件夹先删除，才能成功
     *
     * @param directory 文件夹名
     * @return 删除成功返回true, 失败返回false
     */
    private static boolean deleteDirectory(String directory) {
        // directory不以文件分隔符（/或\）结尾时，自动添加文件分隔符，不同系统下File.separator方法会自动添加相应的分隔符
        if (!directory.endsWith(File.separator)) {
            directory = directory + File.separator;
        }
        File directoryFile = new File(directory);
        // 判断directory对应的文件是否存在，或者是否是一个文件夹
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            System.out.println("文件夹删除失败，文件夹不存在" + directory);
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件和文件夹
        File[] files = directoryFile.listFiles();
        for (File file : files) {  // 循环删除所有的子文件及子文件夹
            // 删除子文件
            if (file.isFile()) {
                flag = deleteFile(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {  // 删除子文件夹
                flag = deleteDirectory(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("删除失败");
            return false;
        }
        // 最后删除当前文件夹
        if (directoryFile.delete()) {
            System.out.println("文件夹删除成功：" + directory);
            return true;
        } else {
            System.out.println("文件夹删除失败：" + directory);
            return false;
        }
    }

    public static boolean isImage(String fileName) {
        String mimeType = getMimeType(fileName);
        return !TextUtils.isEmpty(fileName) && NullUtil.nonNull(mimeType) && mimeType.contains(PREFIX_IMAGE);
    }

    /**
     * Get the Mime Type from a File
     *
     * @param fileName 文件名
     * @return 返回MIME类型
     */
    public static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(fileName);
    }


    /**
     * 根据文件后缀名判断 文件是否是视频文件
     *
     * @param fileName 文件名
     * @return 是否是视频文件
     */
    public static boolean isVideo(String fileName) {
        String mimeType = getMimeType(fileName);
        return !TextUtils.isEmpty(fileName) && NullUtil.nonNull(mimeType) && mimeType.contains(PREFIX_VIDEO);
    }

    public static List getFileInfo(String p) throws IOException {
        File file = new File(p);
        String type = file.isFile() ? "File" : "Folder";
        Path path = Paths.get(file.getAbsolutePath());
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        // 从基本属性类中获取文件创建时间
        FileTime creationTime = attrs.creationTime();
        FileTime lastAccessTime = attrs.lastAccessTime();
        FileTime lastModifiedTime = attrs.lastModifiedTime();
        // 将文件创建时间转成毫秒
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date creationDateTime = new Date();
        Date lastAccessDateTime = new Date();
        Date lastModifiedDateTime = new Date();
        creationDateTime.setTime(creationTime.toMillis());
        lastAccessDateTime.setTime(lastAccessTime.toMillis());
        lastModifiedDateTime.setTime(lastModifiedTime.toMillis());
        // 毫秒转成时间字符串
        List<String> infoList = new ArrayList<>();
        infoList.add(type + " creation date: " + dateFormat.format(creationDateTime));
        infoList.add(type + " last access date: " + dateFormat.format(lastAccessDateTime));
        infoList.add(type + " last modified date: " + dateFormat.format(lastModifiedDateTime));
        int size = infoList.size();
        for (int i = 0; i < size; i++) {
            Console.println(infoList.get(i), Console.BOLD);
        }
        return infoList;
    }

}
