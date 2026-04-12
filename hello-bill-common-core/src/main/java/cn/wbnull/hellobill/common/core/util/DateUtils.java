package cn.wbnull.hellobill.common.core.util;

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

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_ONLY_FORMAT = "yyyy-MM-dd";
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    private DateUtils() {
    }

    public static String dateFormat() {
        return new SimpleDateFormat(DEFAULT_FORMAT, Locale.CHINA).format(new Date());
    }

    public static String dateFormat(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DATE_ONLY_FORMAT));
    }

    public static String dateFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT)).replace("T", " ");
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat(DEFAULT_FORMAT, Locale.CHINA).format(date);
    }

    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(date);
    }

    public static Date dateParse(String dateValue, String fromFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(fromFormat)) {
            return null;
        }
        return new SimpleDateFormat(fromFormat).parse(dateValue);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZONE_OFFSET).toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZONE_OFFSET).toLocalDateTime();
    }

    public static LocalDate atStartOfMonth(long monthsToSubtract) {
        return YearMonth.now().minusMonths(monthsToSubtract).atDay(1);
    }

    public static LocalDate atStartOfYear(long yearsToSubtract) {
        return Year.now().minusYears(yearsToSubtract).atDay(1);
    }
}
