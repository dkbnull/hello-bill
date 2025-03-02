package cn.wbnull.hellobill.common.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Logger 工具类
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
@Slf4j
public class LoggerUtils {

    private LoggerUtils() {
    }

    public static void info(String message) {
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    public static void info(String position, String method, String content) {
        if (log.isInfoEnabled()) {
            log.info("[{}]\n方法：{}\n参数：{}\n", position, method, content);
        }
    }

    public static void error(String message) {
        if (log.isErrorEnabled()) {
            log.error(message);
        }
    }

    public static void error(String position, String method, String content) {
        if (log.isErrorEnabled()) {
            log.error("[{}]\n方法：{}\n参数：{}\n", position, method, content);
        }
    }

    public static void error(String message, Throwable t) {
        if (log.isErrorEnabled()) {
            log.error(message, t);
        }
    }

    public static void error(String position, String method, String content, Throwable t) {
        if (log.isErrorEnabled()) {
            log.error("[{}]\n方法：{}\n参数：{}\n", position, method, content, t);
        }
    }
}
