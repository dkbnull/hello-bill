package cn.wbnull.hellobill.common.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * BigDecimal 工具类
 *
 * @author null
 * @date 2019-03-29
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    public static String format2Decimal(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value.stripTrailingZeros().doubleValue());
    }

    public static boolean isEqual(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) == 0;
    }
}
