package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class BigDecimalUtil {
	private static final Logger log = LogManager.getLogger(BigDecimalUtil.class.getName());

	public static int compare(BigDecimal a, BigDecimal b) {
		log.info("1:表示A>B; 0:表示A=B; -1:表示A<B");
		return a.compareTo(b);
	}


	//bigDecimal  转换成  Long类型
	public static Long toLong(BigDecimal bigDecimal){
		if (NullUtil.isNull(bigDecimal)  ){
		    return 0l;
		}
		Long c = bigDecimal.longValue();
		return c;
	}

	/**
	 * 1E+11
	 *
	 * @param bigDecimal
	 * @return
	 */
	public static String convertToString(BigDecimal bigDecimal) {
		return bigDecimal.toString();
	}

	/**
	 * 100000000000
	 *
	 * @param bigDecimal
	 * @return
	 */
	public static String toPlainText(BigDecimal bigDecimal) {
		return bigDecimal.toPlainString();
	}

	/**
	 * 100E+9
	 *
	 * @param bigDecimal
	 * @return
	 */
	public static String toEngineeringString(BigDecimal bigDecimal) {
		return bigDecimal.toEngineeringString();
	}

	/**
	 * 方法名：sub
	 * 功能：减法运算
	 * 入参：v1:被减数 ,v2:减数
	 * 出参：两个参数的差
	 */
	public static Double sub(Double v1, Double v2) {
		if (NullUtil.isNull(v1, v2)) {
			throw new NullPointerException("参数不能为空");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 方法名：add
	 * 功能：加法运算
	 * 入参：v1:被加数 ,v2:加数
	 * 出参：两个参数的和
	 */
	public static Double add(Double v1, Double v2) {
		if (NullUtil.isNull(v1, v2)) {
			throw new NullPointerException("参数不能为空");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 方法名：mul
	 * 功能：乘法运算
	 * 入参：v1：被乘数，v2：乘数
	 * 出参：两个参数的积
	 */
	public static Double multiple(Double v1, Double v2) {
		if (NullUtil.isNull(v1, v2)) {
			throw new NullPointerException("参数不能为空");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 方法名：div
	 * 功能：除法运算，当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 入参：v1：被除数，v2：除数，scale：表示表示需要精确到小数点以后几位
	 * 出参：两个参数的商
	 */
	public static Double div(Double v1, Double v2, Integer scale) {
		if (NullUtil.isNull(v1, v2)) {
			throw new NullPointerException("参数不能为空");
		}
		if (scale == null) {
			scale = 2;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/** 保留两位小数
	 * @param amount
	 * @return
	 */
	public static String format(String amount) {
        return TypeUtil.format(amount);
	}

	/**
	 * 方法名：isNumeric
	 * 功能：是否为整数
	 * 入参：
	 * 出参：
	 */
	public static boolean isInteger(String str) {
		if (NullUtil.isNull(str)) {
			throw new NullPointerException("参数不能为空");
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/** 整數 return true, 其他return false
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
        return TypeUtil.isNumeric(str);
	}



}
