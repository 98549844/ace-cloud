package com.ace;


import java.util.ArrayList;
import java.util.List;

import static com.ace.utilities.ConsoleTable.println;

/**
 * @Classname: ${NAME}
 * @Date: 21/3/2024 5:08 pm
 * @Author: garlam
 * @Description:
 */
public class Main {
    public static void main(String[] args) {

        List<String> header = new ArrayList<>();
        header.add("application");
        header.add("external port");
        header.add("internal port");
        header.add("protocol");
        header.add("internal ip");

        List<String[]> body = new ArrayList<>();
        body.add(new String[]{"openvpn-1194", "1194", "1194","both","192.168.1.100"});
        body.add(new String[]{"openvpn-943", "943", "943","both","192.168.1.100"});
        body.add(new String[]{"openvpn-9090", "9090", "1194","both","192.168.1.100"});

        println(header, body);



    }



}
