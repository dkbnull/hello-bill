package cn.wbnull.hellobill.common.constant;

import lombok.Getter;

/**
 * 返回参数code枚举
 *
 * @author dukunbiao(null)  2024-11-30
 */
@Getter
public enum ResponseCodeEnum {

    FAIL("1000", "失败"),
    TOKEN_EXPIRED("1100", "token过期"),
    SUCCESS("2000", "成功"),
    ;

    private final String code;
    private final String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
