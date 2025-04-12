package cn.wbnull.hellobill.common.core.constant;

import lombok.Getter;

/**
 * 返回参数code枚举
 *
 * @author null
 * @date 2024-11-30
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Getter
public enum ResponseCodeEnum {

    SUCCESS("1000", "成功"),
    FAIL("2000", "失败"),
    INVALID_TOKEN("3000", "无效的访问令牌，请刷新授权令牌或重新授权获取新的令牌"),
    TOKEN_TIME_OUT("3001", "访问令牌已过期，请刷新授权令牌或重新授权获取新的令牌"),
    INVALID_AUTH_TOKEN("3100", "无效的授权令牌，请刷新授权令牌或重新授权获取新的令牌"),
    INVALID_AUTH_TOKEN_NO_API("3101", "未授权当前接口，请重新授权获取新的授权令牌"),
    AUTH_TOKEN_TIME_OUT("3102", "授权已过期，请刷新授权令牌或重新授权获取新的令牌"),
    MISSING_PARAMETER("4000", "缺少必选参数"),
    INVALID_PARAMETER("4001", "非法的参数，检查参数，格式不对、非法值、越界等"),
    INVALID_SIGN("4100", "验签出错"),
    FORBIDDEN_API("4200", "接口被禁用"),
    METHOD_CALL_LIMITED("4201", "调用频次超限"),
    ;

    private final String code;
    private final String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
