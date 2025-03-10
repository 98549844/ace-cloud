package com.ace;


import com.ace.utilities.FileUtil;

import java.io.IOException;

/**
 * @Classname: ${NAME}
 * @Date: 21/3/2024 5:08 pm
 * @Author: garlam
 * @Description:
 */

//https://logging.apache.org/log4j/2.x/manual/configuration.html
public class AceUtilities {


    public static void main(String[] args) throws IOException {
        String p = "C:\\Users\\garlam.au\\IdeaProjects\\ace-cloud\\ace-utilities\\src\\main\\java\\com\\ace";
        FileUtil.count(p);
    }


}
