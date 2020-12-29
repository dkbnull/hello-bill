package cn.wbnull.hellobill.common.util;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 *
 * @author dukunbiao(null)  2019-03-29
 * https://github.com/dkbnull/HelloUtil
 */
public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    /**
     * 加
     *
     * @param value1 加数
     * @param value2 加数
     * @return 和
     */
    public static int add(int value1, int value2) {
        return BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).stripTrailingZeros().intValue();
    }

    /**
     * 加
     *
     * @param value1 加数
     * @param value2 加数
     * @return 和
     */
    public static double add(double value1, double value2) {
        return BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).stripTrailingZeros().doubleValue();
    }

    /**
     * 加
     *
     * @param value1 加数
     * @param value2 加数
     * @return 和
     */
    public static String add(String value1, String value2) {
        return new BigDecimal(value1).add(new BigDecimal(value2)).stripTrailingZeros().toPlainString();
    }

    /**
     * 减
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 差
     */
    public static int subtract(int value1, int value2) {
        return BigDecimal.valueOf(value1).subtract(BigDecimal.valueOf(value2)).stripTrailingZeros().intValue();
    }

    /**
     * 减
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 差
     */
    public static double subtract(double value1, double value2) {
        return BigDecimal.valueOf(value1).subtract(BigDecimal.valueOf(value2)).stripTrailingZeros().doubleValue();
    }

    /**
     * 减
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 差
     */
    public static String subtract(String value1, String value2) {
        return new BigDecimal(value1).subtract(new BigDecimal(value2)).stripTrailingZeros().toPlainString();
    }

    /**
     * 乘
     *
     * @param value1 乘数
     * @param value2 乘数
     * @return 积
     */
    public static int multiply(int value1, int value2) {
        return BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2)).stripTrailingZeros().intValue();
    }

    /**
     * 乘
     *
     * @param value1 乘数
     * @param value2 乘数
     * @return 积
     */
    public static double multiply(double value1, double value2) {
        return BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2)).stripTrailingZeros().doubleValue();
    }

    /**
     * 乘
     *
     * @param value1 乘数
     * @param value2 乘数
     * @return 积
     */
    public static String multiply(String value1, String value2) {
        return new BigDecimal(value1).multiply(new BigDecimal(value2)).stripTrailingZeros().toPlainString();
    }

    /**
     * 乘
     *
     * @param value1       乘数
     * @param value2       乘数
     * @param scale        精度
     * @param roundingMode 舍入模式
     * @return 积
     */
    public static double multiply(double value1, double value2, int scale, int roundingMode) {
        return new BigDecimal(value1).multiply(new BigDecimal(value2)).setScale(scale, roundingMode).stripTrailingZeros().doubleValue();
    }

    /**
     * 乘
     *
     * @param value1       乘数
     * @param value2       乘数
     * @param scale        精度
     * @param roundingMode 舍入模式
     * @return 积
     */
    public static String multiply(String value1, String value2, int scale, int roundingMode) {
        return new BigDecimal(value1).multiply(new BigDecimal(value2)).setScale(scale, roundingMode).stripTrailingZeros().toPlainString();
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static double divide(int value1, int value2, int roundingMode) {
        return BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), roundingMode).stripTrailingZeros().doubleValue();
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static double divide(double value1, double value2, int roundingMode) {
        return BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), roundingMode).stripTrailingZeros().doubleValue();
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static String divide(String value1, String value2, int roundingMode) {
        return new BigDecimal(value1).divide(new BigDecimal(value2), roundingMode).stripTrailingZeros().toPlainString();
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param scale        精度
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static double divide(int value1, int value2, int scale, int roundingMode) {
        return BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), scale, roundingMode).stripTrailingZeros().doubleValue();
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param scale        精度
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static double divide(double value1, double value2, int scale, int roundingMode) {
        return BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), scale, roundingMode).stripTrailingZeros().doubleValue();
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param scale        精度
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static String divide(String value1, String value2, int scale, int roundingMode) {
        return new BigDecimal(value1).divide(new BigDecimal(value2), scale, roundingMode).stripTrailingZeros().toPlainString();
    }

    /**
     * 数值格式化
     *
     * @param value 待格式化数值
     * @return 格式化后数值
     */
    public static double format(double value) {
        return BigDecimal.valueOf(value).stripTrailingZeros().doubleValue();
    }

    /**
     * 数值格式化
     *
     * @param value 待格式化数值
     * @return 格式化后数值
     */
    public static String format(String value) {
        return new BigDecimal(value).stripTrailingZeros().toPlainString();
    }

    /**
     * 比较大小
     *
     * @param value1
     * @param value2
     * @return -1:<, 0:=, 1:>
     */
    public static int compareTo(double value1, double value2) {
        return BigDecimal.valueOf(value1).compareTo(BigDecimal.valueOf(value2));
    }

    /**
     * 比较大小
     *
     * @param value1
     * @param value2
     * @return -1:<, 0:=, 1:>
     */
    public static int compareTo(String value1, String value2) {
        return new BigDecimal(value1).compareTo(new BigDecimal(value2));
    }
}
