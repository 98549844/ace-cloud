package com.ace.utilities;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

/**
 * @Classname: CsvUtil
 * @Date: 8/5/2024 9:45 AM
 * @Author: garlam.au
 * @Description:
 */


public class CsvUtil {
    private static final Logger log = LogManager.getLogger(CsvUtil.class.getName());

    public static void main(String[] args) throws IOException {


    }

    public static void toCsv(String jsonFilePath, String csvFilePath) throws IOException {
        // 读取JSON文件
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray jsonArray;
        JSONObject jsonObject;

        // 创建CSV文件
        FileWriter writer = new FileWriter(csvFilePath);
        if (JSON.isValidArray(jsonContent)) {
            jsonArray = JSON.parseArray(jsonContent);
            // 创建CSV文件

            // 获取JSON对象的键集合，用作CSV的头部
            Set<String> keys = jsonArray.getJSONObject(0).keySet();
            String headers = String.join(",", keys);
            writer.write(headers + "\n");

            // 遍历JSON数组，写入CSV
            int rowSize = jsonArray.size();
            for (int i = 0; i < rowSize; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String[] values = new String[keys.size()];
                int index = 0;
                for (String key : keys) {
                    values[index++] = jsonObject.getString(key);
                }
                String csvRow = String.join(",", values);
                writer.write(csvRow + "\n");
            }
        } else {
            jsonObject = JSON.parseObject(jsonContent);
            Set<String> keys = jsonObject.keySet();

            String headers = String.join(",", keys);
            writer.write(headers + "\n");

            String[] values = new String[keys.size()];
            int index = 0;
            for (String key : keys) {
                values[index++] = jsonObject.getString(key);
            }
            String csvRow = String.join(",", values);
            writer.write(csvRow + "\n");
        }
        // 关闭CSV文件
        writer.flush();
        writer.close();

    }


    //public static void mapToCsv(Map<String, String> map, String csvFileName) throws IOException {
    //    try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFileName), CSVFormat.DEFAULT)) {
    //        map.forEach((key, value) -> {
    //            try {
    //                csvPrinter.printRecord(key, value);
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        });
    //    }
    //}
    //
    //public static void listToCsv(List<String> list, String csvFileName) throws IOException {
    //    try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFileName), CSVFormat.DEFAULT)) {
    //        list.forEach(item -> {
    //            try {
    //                csvPrinter.printRecord(item);
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        });
    //    }
    //}


}

