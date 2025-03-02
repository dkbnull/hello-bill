package cn.wbnull.hellobill.common.core.constant;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author null  2022-01-04
 */
@Getter
public enum Status {

    FORBIDDEN("0"),
    USABLE("1"),
    ;

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
