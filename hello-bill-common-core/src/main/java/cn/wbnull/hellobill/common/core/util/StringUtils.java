package cn.wbnull.hellobill.common.core.util;

/**
 * String 工具类
 *
 * @author null
 * @date 2018-07-26
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String value) {
        int length;
        if (value == null || (length = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean areNotEmpty(String... values) {
        if (values == null || values.length == 0) {
            return false;
        }

        boolean result = true;
        for (String value : values) {
            result &= !isEmpty(value);
        }
        return result;
    }
}
