package cn.wbnull.hellobill.common.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Logger 工具类
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Slf4j
public class LoggerUtils {

    public static void info(String message) {
        log.info(message);
    }

    public static void info(String position, String method, String content) {
        log.info(toMessage(position, method, content));
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String position, String method, String content) {
        log.error(toMessage(position, method, content));
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    public static void error(String position, String method, String content, Throwable t) {
        log.error(toMessage(position, method, content), t);
    }

    private static String toMessage(String position, String method, String content) {
        return String.format("[%s]\n方法：%s\n参数：%s\n", position, method, content);
    }
}
