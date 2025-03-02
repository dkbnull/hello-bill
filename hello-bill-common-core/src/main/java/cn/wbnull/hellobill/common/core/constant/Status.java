package cn.wbnull.hellobill.common.core.constant;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author null
 * @date 2022-01-04
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
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
