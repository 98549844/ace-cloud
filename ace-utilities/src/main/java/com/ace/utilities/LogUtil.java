package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
public class LogUtil {
    private final static Logger log = LogManager.getLogger(LogUtil.class.getName());

    public static void main(String[] args) {
        log.traceEntry();
        log.trace("我是trace");
        log.info("我是info信息:{}", "ace-utilities info");
        log.warn("我是warn信息:{}", "ace-utilities warn");
        log.error("我是error");
        log.fatal("我是fatal");
        log.debug("我是debug");
        log.trace("退出程序.");
        log.traceExit();
    }

    public static void removeRedundantLogExt(String path, List<String> ls) throws Exception {
        int i = 0;
        path = FileUtil.getParent(path);
        ArrayList<String> newLs = new ArrayList<>();
        for (String fs : ls) {
            if (FileUtil.isFile(path + fs)) {
                String[] fSet = fs.split("\\.");
                ++i;
                String newFileName = fSet[0] + "." + fSet[1];
                if (newLs.contains(newFileName)) {
                    newFileName = fSet[0] + "_" + i + "." + fSet[1];
                }
                newLs.add(newFileName);
            }
        }
        FileUtil.renameFilesName(path, newLs);
    }

}
