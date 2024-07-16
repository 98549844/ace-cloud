package com.ace.utilities.excel;

import com.ace.utilities.ReflectionsUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 */
//https://blog.csdn.net/jasnet_u/article/details/111502178
public class ImportExcel {
    private static final Logger log = LogManager.getLogger(ImportExcel.class.getName());

    //工作薄对象
    private final Workbook workbook;
    //工作表对象
    private final Sheet sheet;
    //标题行号
    private final int headerNum;

    /**
     * 构造函数
     *
     * @param filePath  导入文件，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String filePath, int headerNum) throws InvalidFormatException, IOException {
        this(new File(filePath), headerNum);
    }

    /**
     * 构造函数
     *
     * @param file      导入文件对象，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum) throws InvalidFormatException, IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     *
     * @param filePath   导入文件
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String filePath, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        this(new File(filePath), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param file       导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }


    /**
     * 构造函数
     *
     * @param filePath   导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String filePath, InputStream is, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(filePath)) {
            throw new RuntimeException("excel文件不存在, 請檢查路徑 !");
        } else if (filePath.toLowerCase().endsWith("xls")) {
            this.workbook = new HSSFWorkbook(is);
        } else if (filePath.toLowerCase().endsWith("xlsx")) {
            this.workbook = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.workbook.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("Excel文檔中沒有該工作表.");
        }
        this.sheet = this.workbook.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        log.info("Initialize success.");
    }

    @SuppressWarnings("deprecation")
    public static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getLastCellNum();
        for (int c = firstCellNum; c < lastCellNum; c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取行对象
     *
     * @param rownum
     * @return
     */
    public Row getRow(int rownum) {
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     *
     * @return
     */
    public int getDataRowNum() {
        return headerNum + 1;
    }

    /**
     * 获取最后一个数据行号
     *
     * @return
     */
    public int getRowNum() {
        return this.sheet.getLastRowNum() + headerNum;
    }

    /**
     * 获取最后一个列号
     *
     * @return
     */
    public int getLastCellNum() {
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     * cellValue 由0開始
     */
    public Object getCellValue(Row row, int column) {
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    // val = cell.getNumericCellValue();
                    // 当excel 中的数据为数值或日期是需要特殊处理
                    if (DateUtil.isCellDateFormatted(cell)) {
                        double d = cell.getNumericCellValue();
                        Date date = DateUtil.getJavaDate(d);
                        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
                        val = dformat.format(date);
                    } else {
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setGroupingUsed(false);// true时的格式：1,234,567,890
                        val = nf.format(cell.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.FORMULA) {
                    val = cell.getCellFormula();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return val;
        }
        return val;
    }

    /**
     * 获取导入数据列表
     *
     * @param cls    导入对象类型
     *               Class<E> cls excel對應的java封裝的pojo對像
     * @param groups 导入分组
     *               refer to 数据excel导入,导出案例
     *               https://blog.csdn.net/jasnet_u/article/details/111502178
     */
    public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException {
        List<Object[]> annotationList = Lists.newArrayList();
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, f});
                }
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, m});
                }
            }
        }
        // Field sorting
        Collections.sort(annotationList, new Comparator<>() {
            public int compare(Object[] o1, Object[] o2) {
                return Integer.compare(((ExcelField) o1[0]).sort(), ((ExcelField) o2[0]).sort());
            }
        });
        // Get excel data
        List<E> dataList = Lists.newArrayList();
        for (int i = this.getDataRowNum(); i < this.getRowNum(); i++) {
            E e = cls.newInstance();
            int column = 0;
            Row row = this.getRow(i);
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                Object val = this.getCellValue(row, column++);
                if (val != null) {
                    ExcelField ef = (ExcelField) os[0];
                    // If is dict type, get dict value
                    //if (StringUtils.isNotBlank(ef.dictType())) {
                    // dictType不为空，表示自定义的字典类型的，通过字典和相应类型去取值
                    // val = DictUtils.getDictValue(val.toString(),
                    // ef.dictType(), "");
                    // log.debug("Dictionary type value: ["+i+","+colunm+"]
                    // " + val);
                    //}
                    // Get param type and type cast
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field) {
                        valType = ((Field) os[1]).getType();
                    } else if (os[1] instanceof Method method) {
                        if ("get".equals(method.getName().substring(0, 3))) {
                            valType = method.getReturnType();
                        } else if ("set".equals(method.getName().substring(0, 3))) {
                            valType = ((Method) os[1]).getParameterTypes()[0];
                        }
                    }
                    try {
                        // 如果导入的java对象，需要在这里自己进行变换。
                        if (valType == String.class) {
                            String s = String.valueOf(val.toString());
                            if (StringUtils.endsWith(s, ".0")) {
                                val = StringUtils.substringBefore(s, ".0");
                            } else {
                                val = String.valueOf(val.toString());
                            }
                        } else if (valType == Integer.class) {
                            val = Double.valueOf(val.toString()).intValue();
                        } else if (valType == Long.class) {
                            val = Double.valueOf(val.toString()).longValue();
                        } else if (valType == Double.class) {
                            val = Double.valueOf(val.toString());
                        } else if (valType == Float.class) {
                            val = Float.valueOf(val.toString());
                        } else if (valType == Date.class) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            val = sdf.parse(val.toString());
                        } else {
                            if (ef.fieldType() != Class.class) {
                                val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
                            } else {
                                val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype." + valType.getSimpleName() + "Type")).getMethod("getValue", String.class).invoke(null, val.toString());
                            }
                        }
                    } catch (Exception ex) {
                        log.info("Get cell value [{},{}] error: {}", i, column, ex.toString());
                        val = null;
                    }
                    // set entity value
                    if (os[1] instanceof Field) {
                        ReflectionsUtil.invokeSetter(e, ((Field) os[1]).getName(), val);
                    } else if (os[1] instanceof Method) {
                        String methodName = ((Method) os[1]).getName();
                        if ("get".equals(methodName.substring(0, 3))) {
                            methodName = "set" + StringUtils.substringAfter(methodName, "get");
                        }
                        ReflectionsUtil.invokeMethod(e, methodName, new Class[]{valType}, new Object[]{val});
                    }
                }
                sb.append(val).append(", ");
            }
            dataList.add(e);
            log.info("Read success: [{}] {}", i, sb.toString());
        }
        return dataList;
    }


}
