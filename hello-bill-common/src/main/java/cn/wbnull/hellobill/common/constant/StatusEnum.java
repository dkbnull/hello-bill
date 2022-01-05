package cn.wbnull.hellobill.common.constant;

/**
 * Status枚举
 *
 * @author dukunbiao(null)  2022-01-04
 */
public enum StatusEnum {

    FORBIDDEN("0"),
    USABLE("1"),
    OTHER("");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
