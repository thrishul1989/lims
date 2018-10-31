package com.todaysoft.lims.utils;

import java.math.BigDecimal;

/**
 * double 运算 .
 *
 */
public class DoubleCalculateUtils {

    /**
     * 提供精确的加法运算。
     * @param v1
     * @param v2
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 提供精确的除法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @param len 保留到几位小数
     * @return 两个参数的积
     */
    public static double div(double v1, double v2,int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 四舍五入
     * @param d
     * @param len 保留到几位小数
     * @return
     */
    public static double round(double d, int len) {     // 进行四舍五入
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，
        return b1.divide(b2, len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 比较两个double
     * @param v1
     * @param v2
     * @return v1是否大于v2 -1 小于 0等于 1大于
     */
    public static int compare(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }
}
