package cn.wbnull.hellobill.common.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具类，基于 BCrypt 算法
 *
 * @author null
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
public class PasswordUtils {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordUtils() {
    }

    /**
     * 使用 BCrypt 加密密码
     *
     * @param rawPassword 原始密码
     * @return BCrypt 加密后的密码
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 验证密码是否匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
