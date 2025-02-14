package com.ace.utilities;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ace.constants.constant.UTF_8;

/**
 * @Classname: CsvUtil
 * @Date: 8/5/2024 9:45 AM
 * @Author: garlam.au
 * @Description:
 */

//https://blog.csdn.net/lydms/article/details/118641389
public class CsvUtil {
    private static final Logger log = LogManager.getLogger(CsvUtil.class.getName());

    public static void main(String[] args) throws IOException {

        // 遍历表格数据
        //for (Table.Cell<Integer, Integer, String> cell : table1.cellSet()) {
        //    System.out.println(cell.getRowKey() + " : " + cell.getColumnKey() + " -> " + cell.getValue());
        //}
    }

    public static Table<Integer, Integer, String> toTable(File file, boolean haveHeader) {
        Table<Integer, Integer, String> csvTable = TreeBasedTable.create();
        int i = 0;
        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            for (String[] next : csvReader) { // 第一行必讀, 暫時不支持去除表頭
                int j = 0;
                for (String val : next) {
                    csvTable.put(i, j, val);
                    j++;
                }
                i++;
            }
            if (haveHeader) {
                return csvTable;
            }
            // 移除座標x,y的cell
            //csvTable.remove(x, y);

            // remote header row
            csvTable.rowKeySet().remove(0);
            return csvTable;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CSV文件读取异常: {}", e.getMessage());
            return csvTable;
        }
    }


    /**
     * 解析csv文件并转成bean
     *
     * @param file csv文件
     * @return 数组
     */
    public static List<String[]> toList(File file, boolean haveHeader) {
        int header = 1;
        if (haveHeader) {
            header = 0;
        }
        int i = 0;
        List<String[]> list = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            for (String[] next : csvReader) {
                if (i >= header) {
                    list.add(next);
                }
                i++;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CSV文件读取异常: {}", e.getMessage());
            return list;
        }
    }

    /**
     * 解析csv文件并转成bean
     * <p>
     * 在 entity的LocalDate field加入註解用作格式轉換
     *
     * @param file  csv文件
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     * @CsvDate(value = "yyyy-MM-dd HH:mm:ss.SSSSSS")
     */
    public static <T> List<T> toEntities(File file, Class<T> clazz) {
        InputStreamReader in;
        CsvToBean<T> csvToBean;
        try {
            InputStream inputStream = new FileInputStream(file);
            in = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            csvToBean = new CsvToBeanBuilder<T>(in).withMappingStrategy(strategy).build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据转化失败");
            return null;
        }
        return csvToBean.parse();
    }


    /**
     * json转csv
     * 同时支持读取文本和jsonString
     *
     * @param json
     * @param csvFilePath
     * @throws IOException
     */
    public static void toCsv(String json, String csvFilePath) throws IOException {
        // 读取JSON文件
        String jsonContent;
        File file = new File(json);
        if (file.isFile()) {
            jsonContent = new String(Files.readAllBytes(Paths.get(json)));
        } else {
            jsonContent = json;
        }

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


    /**
     * csv写入 fileName.txt
     *
     * @param path
     * @param fileName
     * @param content
     */
    public static void toFile(String path, String fileName, String content) {
        FileUtil.write(path, fileName, content, true);
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

