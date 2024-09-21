package com.ace;


import com.ace.utilities.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.ace.utilities.FileUtil.getFileMap;

/**
 * @Classname: ${NAME}
 * @Date: 21/3/2024 5:08 pm
 * @Author: garlam
 * @Description:
 */
public class Main {
    public static void main(String[] args) {


        String aa = "/Users/garlam/IdeaProjects/ace-cloud/ace-utilities/src/main/resources/file/images/temp/img_cat2.png";

        Map<String, String> map = getFileMap(aa);
        System.out.println(map.get(FileUtil.PATH));
        System.out.println(map.get(FileUtil.FILENAME_WITH_EXT));
        System.out.println(map.get(FileUtil.FILENAME));
        System.out.println(map.get(FileUtil.EXT));




    }

    public static Map<String, String> getPathAndFileMap(String f) {
        int i = f.lastIndexOf(File.separator);
        String p = f.substring(0, i);
        String fNameWithExt = f.substring(i + 1); // +1 为了去除分隔符

        String name;
        String ext;
        int k = fNameWithExt.lastIndexOf('.');
        Map<String, String> map = new HashMap<>();
        if (i > 0 && k < f.length() - 1) {
            name = fNameWithExt.substring(0, k);
            ext = fNameWithExt.substring(k);
            map.put(FileUtil.PATH, p);
            map.put(FileUtil.FILENAME_WITH_EXT, fNameWithExt);
            map.put(FileUtil.FILENAME, name);
            map.put(FileUtil.EXT, ext);
        }
        return map;
    }
}
