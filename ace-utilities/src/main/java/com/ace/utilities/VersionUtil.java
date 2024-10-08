package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.SpringVersion;

//import static com.ace.tools.MavenTool.*;

/**
 * @Classname: SpringUtil
 * @Date: 2022/8/4 上午 11:44
 * @Author: kalam_au
 * @Description:
 */


public class VersionUtil {
    private static final Logger log = LogManager.getLogger(VersionUtil.class.getName());


    public static void main(String[] args) {
        getSpringFrameworkVersion();
      //  mavenVersion();
    }

    public static String getSpringFrameworkVersion() {
        String springVersion = SpringVersion.getVersion();
        Console.println("Spring Framework Version: " + SpringVersion.getVersion(), Console.BOLD);
        return springVersion;
    }

/*    public static void mavenVersion() {
        String command = "-v";
        List<String> commands = new ArrayList<>();
        commands.add(mavenWindowsHome);
        commands.add(command);
        run(preparedCommands(commands));
    }*/



}

