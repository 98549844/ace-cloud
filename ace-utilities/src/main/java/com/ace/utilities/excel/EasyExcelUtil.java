package com.ace.utilities.excel;

import com.ace.utilities.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname: EasyExcelUtil
 * @Date: 6/12/2021 3:09 上午
 * @Author: garlam
 * @Description:
 */

//https://blog.csdn.net/weixin_73077810/article/details/131786254
public class EasyExcelUtil extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger log = LogManager.getLogger(EasyExcelUtil.class.getName());

    public static String XLS = "xls";
    public static String XLSX = "xlsx";


    private static final int BATCH_COUNT = 3000;

    public static void main(String[] args) {
        EasyExcelUtil easyExcelUtil = new EasyExcelUtil();
        easyExcelUtil.read("C:\\Users\\Garlam.Au\\IdeaProjects\\ace\\src\\main\\resources\\files\\output\\excel.xls");
    }

    private boolean isNotSupport(String fileName) {
        if (XLS.equals(FileUtil.getExtension(fileName))) {
            return false;
        }
        log.error("unsupported file");
        return true;
    }



    public void read(String fileName) {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行

        //Gson gson = new Gson();
        //EasyExcel.read(fileName, obj.getClass(), new PageReadListener<Users>(dataList -> {
        //    int size = dataList.size();
        //    for (int i = 0; i < size; i++) {
        //        log.info("row{}: {}", i, gson.toJson(dataList.get(i)));
        //    }
        // })).sheet().doRead();
        if (isNotSupport(fileName)) {
            return;
        }
        EasyExcel.read(fileName, new EasyExcelUtil()).sheet().doRead();
    }

    public void read(String fileName, Integer sheetNo) {
        if (isNotSupport(fileName)) {
            return;
        }
        EasyExcel.read(fileName, new EasyExcelUtil()).sheet(sheetNo).doRead();
    }

    public void read(String fileName, String sheetName) {
        if (isNotSupport(fileName)) {
            return;
        }
        EasyExcel.read(fileName, new EasyExcelUtil()).sheet(sheetName).doRead();
    }


    public ExcelReaderSheetBuilder getSheet(String fileName, Integer sheetNo) {
        if (isNotSupport(fileName)) {
            return null;
        }
        return EasyExcel.read(fileName, new EasyExcelUtil()).sheet(sheetNo);
    }

    public ExcelReaderSheetBuilder getSheet(String fileName, String sheetName) {
        if (isNotSupport(fileName)) {
            return null;
        }
        return EasyExcel.read(fileName, new EasyExcelUtil()).sheet(sheetName);
    }


    /**
     * excel读取后的数据处理手段, 重写invoke方法封装数据和保存数据
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     *                <p>
     *                Map<Integer, String> data 可以改用dataModel封装
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        Gson gson = new Gson();
        List<Map<Integer, String>> list = new ArrayList<>();
        log.info("row data: {}", gson.toJson(data));

        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    // 读取header内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Gson gson = new Gson();
        List<Map<Integer, String>> list = new ArrayList<>();
        log.info("header: {}", gson.toJson(headMap));
    }


    public void write(String saveLocation, List objList, Object obj) {
        // 写法1
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // EasyExcel.write(fileName, Users.class).sheet("模板").doWrite(objList);

        // 写法2
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(saveLocation, obj.getClass()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("template").build();
        excelWriter.write(objList, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
        excelWriter.close();
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }
}
