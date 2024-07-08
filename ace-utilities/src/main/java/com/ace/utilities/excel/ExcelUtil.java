package com.ace.utilities.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//POI简单实例
//https://blog.csdn.net/yk10010/article/details/81911997
public class ExcelUtil {
    private static final Logger log = LogManager.getLogger(ExcelUtil.class);


    final static String path = "C:\\Users\\Garlam.Au\\IdeaProjects\\ace-cloud\\ace-utilities\\src\\main\\java\\com\\ace\\utilities\\excel\\";
    final static String fileName = "template.xls";
    //  static HSSFWorkbook workbook;

    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil();

        HSSFWorkbook workbook = excelUtil.createWorkbook();
        excelUtil.setSheetName(workbook, "report");

        List<String> colNames = new ArrayList<>();
        colNames.add("星期一");
        colNames.add("星期二");
        colNames.add("星期三");
        colNames.add("星期四");
        colNames.add("星期五");
        excelUtil.setHeader(workbook, "report", colNames);
        excelUtil.setBody(workbook, "report", null);
        excelUtil.export(workbook, path + fileName);


    }

    //创建空的Excel
    public HSSFWorkbook createWorkbook() {
        return new HSSFWorkbook();
    }

    public void export(HSSFWorkbook workbook, String filePath) {
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("Excel export status : completed !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HSSFWorkbook setSheetName(HSSFWorkbook workbook, String sheetName) {
        try {
            workbook.createSheet(sheetName);

            /*HSSFCellStyle headerStyle = getStyleHeader(workbook);
            HSSFCellStyle bodyStyle = getStyleBody(workbook);

            HSSFRow row = sheets.get(0).createRow(0);

            List<String> colNames = new ArrayList<>();
            colNames.add("星期一");
            colNames.add("星期二");
            colNames.add("星期三");
            colNames.add("星期四");
            colNames.add("星期五");
            int size = colNames.size();

            for (int i = 0; i < size; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(colNames.get(i));
                cell.setCellStyle(headerStyle);
            }*/

            /*SecureRandom random = new SecureRandom();
            String[] course = {"语文", "数学", "英语", "物理", "化学", "政治", "历史", "音乐", "美术", "体育"};
            for (int rowNo = 1; rowNo <= 7; rowNo++) {
                //  sheets.get(0).setColumnWidth(rowNo - 1, 20 * 256); //自定義設置cell寛度
                sheets.get(0).autoSizeColumn(rowNo - 1); //自動設置cell寛度
                HSSFRow rowHSSF = sheets.get(0).createRow(rowNo);
                for (int cellNo = 0; cellNo <= 4; cellNo++) {
                    int i = random.nextInt(10);
                    HSSFCell cell = rowHSSF.createCell(cellNo);
                    cell.setCellValue(course[i]);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(bodyStyle);
                }
            }*/
            // ExcelUtil.export(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public void setHeader(HSSFWorkbook workbook, String sheetName, List<String> colNames) {
        HSSFSheet sheet = workbook.getSheet(sheetName);
        HSSFCellStyle headerStyle = getStyleHeader(workbook);
        int size = colNames.size();
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < size; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(colNames.get(i));
            cell.setCellStyle(headerStyle);
        }
    }

    public void setBody(HSSFWorkbook workbook, String sheetName, List<Class<T>> type) {
        //  int size = type.size();
        HSSFCellStyle bodyStyle = getStyleBody(workbook);

        HSSFSheet sheet = workbook.getSheet(sheetName);

        SecureRandom random = new SecureRandom();
        String[] course = {"语文", "数学", "英语", "物理", "化学", "政治", "历史", "音乐", "美术", "体育"};
        for (int rowNo = 1; rowNo <= 7; rowNo++) {
            sheet.setColumnWidth(rowNo - 1, 20 * 256); //自定義設置cell寛度
            // sheet.autoSizeColumn(rowNo - 1); //自動設置cell寛度
            HSSFRow rowHSSF = sheet.createRow(rowNo);
            for (int cellNo = 0; cellNo <= 4; cellNo++) {
                int i = random.nextInt(10);
                HSSFCell cell = rowHSSF.createCell(cellNo);
                cell.setCellValue(course[i]);
                cell.setCellType(CellType.STRING);
                cell.setCellStyle(bodyStyle);
            }
        }
    }


    //cell style setting
    public static HSSFCellStyle getStyleHeader(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBold(true);//字体加粗
        font.setFontName("Times New Roman");//设置字体名字
        HSSFCellStyle style = workbook.createCellStyle();//设置样式
        style.setFont(font);//设置的字体
        // style.setWrapText(true);//设置自动换行
        style.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐的样式为居中对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐的样式为居中对齐
        //边框填充
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//上边框颜色
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//右边框颜色
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex()); //下边框颜色
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex()); //左边框颜色
        //背景底色
        style.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    public static HSSFCellStyle getStyleBody(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Times New Roman");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        // style.setWrapText(true); //设置自动换行
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    //单元格合并
    public static void combineCells(String[] args) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("课程表");
            HSSFCellStyle headerStyle = getStyleHeader(workbook);

            HSSFRow row00 = sheet.createRow(0);
            HSSFCell row0_cell00 = row00.createCell(0);
            row0_cell00.setCellType(CellType.STRING);
            row0_cell00.setCellValue("课程表");
            row0_cell00.setCellStyle(headerStyle);

            HSSFCell row10_cell01 = row00.createCell(1);
            row10_cell01.setCellType(CellType.STRING);
            row10_cell01.setCellStyle(headerStyle);

            CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 9);
            sheet.addMergedRegion(cellAddresses);

            FileOutputStream out = new FileOutputStream("D:/课程表.xls");
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单元格格式操作
    public static void cellFormat(String[] args) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("格式转换");
            HSSFRow row0 = sheet.createRow(0);
            /**
             * 时间格式转换
             * 我们用第一排第一个、第二个、第三个单元格都设置当前时间
             * 然后第一个单元格不进行任何操作，第二个单元格用内嵌格式，第三个单元格用自定义
             */
            Date date = new Date();
            HSSFCell row1_cell1 = row0.createCell(0);
            HSSFCell row1_cell2 = row0.createCell(1);
            HSSFCell row1_cell3 = row0.createCell(2);
            row1_cell1.setCellValue(date);
            row1_cell2.setCellValue(date);
            row1_cell3.setCellValue(date);
            HSSFCellStyle style1 = workbook.createCellStyle();
            style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            HSSFCellStyle style2 = workbook.createCellStyle();
            style2.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd hh:m:ss"));
            row1_cell2.setCellStyle(style1);
            row1_cell3.setCellStyle(style2);
            /**
             * 第二排我们进行小数处理
             * 第一个不进行任何处理，第二个我们用内嵌格式保留两位，第三个我们用自定义
             */
            HSSFRow row1 = sheet.createRow(1);
            double db = 3.1415926;
            HSSFCell row2_cell1 = row1.createCell(0);
            HSSFCell row2_cell2 = row1.createCell(1);
            HSSFCell row2_cell3 = row1.createCell(2);
            row2_cell1.setCellValue(db);
            row2_cell2.setCellValue(db);
            row2_cell3.setCellValue(db);
            HSSFCellStyle style3 = workbook.createCellStyle();
            style3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            HSSFCellStyle style4 = workbook.createCellStyle();
            style4.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            row2_cell2.setCellStyle(style3);
            row2_cell3.setCellStyle(style4);
            /**
             * 下面是进行货币的三种形式
             */
            HSSFRow row2 = sheet.createRow(2);
            double money = 12345.6789;
            HSSFCell row3_cell1 = row2.createCell(0);
            HSSFCell row3_cell2 = row2.createCell(1);
            HSSFCell row3_cell3 = row2.createCell(2);
            row3_cell1.setCellValue(money);
            row3_cell2.setCellValue(money);
            row3_cell3.setCellValue(money);
            HSSFCellStyle style5 = workbook.createCellStyle();
            style5.setDataFormat(HSSFDataFormat.getBuiltinFormat("￥#,##0.00"));
            HSSFCellStyle style6 = workbook.createCellStyle();
            style6.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0.00"));
            row3_cell2.setCellStyle(style3);
            row3_cell3.setCellStyle(style4);
            FileOutputStream out = new FileOutputStream("D:/格式转换.xls");
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void calc() {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("基本计算");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellFormula("5*5+2");//可直接赋予一个简单的计算公式
            cell0 = row.createCell(1);
            cell0.setCellValue(20);
            cell0 = row.createCell(2);
            cell0.setCellFormula("A1+B1");
            cell0 = row.createCell(3);
            cell0.setCellFormula("A1-B1");
            cell0 = row.createCell(4);
            cell0.setCellFormula("A1*B1");
            cell0 = row.createCell(5);
            cell0.setCellFormula("A1/B1");
            FileOutputStream out = new FileOutputStream("D:/基本计算.xls");
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SUM函数
    public static void sum(String[] args) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("基本计算");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue(1);
            row.createCell(1).setCellValue(2);
            row.createCell(2).setCellValue(3);
            row.createCell(3).setCellValue(4);
            row.createCell(4).setCellValue(5);
            row.createCell(5).setCellValue(6);
            row.createCell(6).setCellValue(7);
            //第七/八列进行计算,两种都等价A1+B1+C1+D1+E1+F1+G1
            row.createCell(7).setCellFormula("sum(A1,B1,C1,D1,E1,F1,G1)");
            row.createCell(8).setCellFormula("sum(A1:G1)");
            FileOutputStream out = new FileOutputStream("D:/基本计算.xls");
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ABS绝对值、INT取整函数、ROUND四舍五入
    public static void getInteger(String[] args) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("基本计算");
            HSSFRow row0 = sheet.createRow(0);
            row0.createCell(0).setCellValue(-1234);
            row0.createCell(1).setCellValue(5678);

            HSSFRow row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue(23.456);
            row1.createCell(1).setCellValue(-54.562);

            HSSFRow row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue(8.49314);
            row2.createCell(1).setCellValue(12.927);
            /**
             * 取绝对值
             */
            row0.createCell(2).setCellFormula("ABS(A1)");
            row0.createCell(3).setCellFormula("ABS(B1)");
            /**
             * 取整
             */
            row1.createCell(2).setCellFormula("INT(A2)");
            row1.createCell(3).setCellFormula("INT(B2)");
            /**
             * 四舍五入
             */
            row2.createCell(2).setCellFormula("ROUND(A3,1)");
            row2.createCell(3).setCellFormula("ROUND(B3,1)");

            FileOutputStream out = new FileOutputStream("D:/基本计算.xls");
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
