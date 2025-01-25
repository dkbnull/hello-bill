package cn.wbnull.hellobill.common.constant;

import lombok.Getter;

/**
 * Status枚举
 *
 * @author null  2022-01-04
 */
@Getter
public enum StatusEnum {

    FORBIDDEN("0"),
    USABLE("1"),
    OTHER("");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }
}
