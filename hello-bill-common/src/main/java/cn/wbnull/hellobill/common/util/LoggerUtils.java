package cn.wbnull.hellobill.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger 工具类
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public class LoggerUtils {

    private static Logger logger;

    private LoggerUtils() {
    }

    private static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(LoggerUtils.class);
        }

        return logger;
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void info(String position, String method, String content) {
        getLogger().info(toMessage(position, method, content));
    }

    public static void error(String message) {
        getLogger().error(message);
    }

    public static void error(String position, String method, String content) {
        getLogger().error(toMessage(position, method, content));
    }

    public static void error(String message, Throwable t) {
        getLogger().error(message, t);
    }

    public static void error(String position, String method, String content, Throwable t) {
        getLogger().error(toMessage(position, method, content), t);
    }

    private static String toMessage(String position, String method, String content) {
        return String.format("[%s]\n方法：%s\n参数：%s\n", position, method, content);
    }
}
