package cn.wbnull.hellobill.common.constant;

import lombok.Getter;

/**
 * Type枚举
 *
 * @author dukunbiao(null)  2022-01-04
 */
@Getter
public enum TypeEnum {

    EXPEND("0", "支出"),
    INCOME("1", "收入"),
    OTHER("", "其他");

    private final String typeCode;
    private final String typeName;

    TypeEnum(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public static TypeEnum getTypeEnum(String typeCode) {
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.typeCode.equals(typeCode)) {
                return typeEnum;
            }
        }

        return OTHER;
    }
}
