package cn.wbnull.hellobill.common.core.util;

import cn.wbnull.hellobill.common.core.constant.UtilConstants;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Date 工具类
 *
 * @author null
 * @date 2018-08-31
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 时间格式化
     *
     * @return 时间字符串
     */
    public static String dateFormat() {
        return new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).format(new Date());
    }

    public static String dateFormat(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(UtilConstants.DATE_FORMAT_ONLY));
    }

    public static String dateFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(UtilConstants.DATE_FORMAT)).replace("T", " ");
    }

    /**
     * 时间格式化
     *
     * @param date 待格式化时间
     * @return 时间字符串
     */
    public static String dateFormat(Date date) {
        return new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).format(date);
    }

    /**
     * 时间格式化
     *
     * @param format 格式
     * @return 时间字符串
     */
    public static String dateFormat(String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(new Date());
    }

    /**
     * 时间格式化
     *
     * @param date   待格式化时间
     * @param format 格式
     * @return 时间字符串
     */
    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(date);
    }

    /**
     * 时间字符串格式化
     *
     * @param dateValue 待格式化时间字符串
     * @param toFormat  要格式化的格式
     * @return 时间字符串
     * @throws Exception
     */
    public static String dateFormat(String dateValue, String toFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(toFormat)) {
            return null;
        }

        Date date = new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).parse(dateValue);
        return dateFormat(date, toFormat);
    }

    /**
     * 时间字符串格式化
     *
     * @param dateValue  待格式化时间字符串
     * @param fromFormat 待格式化时间字符串格式
     * @param toFormat   要格式化的格式
     * @return 时间字符串
     * @throws Exception
     */
    public static String dateFormat(String dateValue, String fromFormat, String toFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(fromFormat) || StringUtils.isEmpty(toFormat)) {
            return null;
        }

        Date date = new SimpleDateFormat(fromFormat, Locale.CHINA).parse(dateValue);
        return dateFormat(date, toFormat);
    }

    public static String dateFormat(int timestamp) {
        return new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).format(new Date(timestamp * 1000L));
    }

    public static String dateFormat(int timestamp, String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(new Date(timestamp * 1000L));
    }

    public static Date dateParse(String dateValue, String fromFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(fromFormat)) {
            return null;
        }

        return new SimpleDateFormat(fromFormat).parse(dateValue);
    }

    public static LocalDateTime localDateTimeParse(String dateValue) {
        if (StringUtils.isEmpty(dateValue)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UtilConstants.DATE_FORMAT);
        return LocalDateTime.parse(dateValue, dtf);
    }

    public static LocalDate localDateParse(String dateValue) {
        if (StringUtils.isEmpty(dateValue)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UtilConstants.DATE_FORMAT_ONLY);
        return LocalDate.parse(dateValue, dtf);
    }

    public static LocalTime localTimeParse(String dateValue) {
        if (StringUtils.isEmpty(dateValue)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UtilConstants.DATE_FORMAT_TIME_ONLY);
        return LocalTime.parse(dateValue, dtf);
    }

    public static String dateParse(String dateValue, String fromFormat, String toFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(fromFormat) || StringUtils.isEmpty(toFormat)) {
            return null;
        }

        return dateFormat(dateParse(dateValue, fromFormat), toFormat);
    }

    /**
     * 获取Linux时间戳，精确到秒
     *
     * @return Linux时间戳
     */
    public static String currentTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static long toEpochTime(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000;
    }

    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    public static LocalDate atStartOfMonth(long monthsToSubtract) {
        return YearMonth.now().minusMonths(monthsToSubtract).atDay(1);
    }

    public static LocalDate atStartOfYear(long yearsToSubtract) {
        return Year.now().minusYears(yearsToSubtract).atDay(1);
    }
}
