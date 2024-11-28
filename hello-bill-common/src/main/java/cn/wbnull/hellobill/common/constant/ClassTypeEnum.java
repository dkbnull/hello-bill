package cn.wbnull.hellobill.common.constant;

import lombok.Getter;

/**
 * Type枚举
 *
 * @author dukunbiao(null)  2022-01-04
 */
@Getter
public enum ClassTypeEnum {

    EXPEND("0", "支出"),
    INCOME("1", "收入"),
    OTHER("", "其他");

    private final String typeCode;
    private final String typeName;

    ClassTypeEnum(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public static ClassTypeEnum getClassTypeEnum(String typeCode) {
        for (ClassTypeEnum classTypeEnum : ClassTypeEnum.values()) {
            if (classTypeEnum.typeCode.equals(typeCode)) {
                return classTypeEnum;
            }
        }

        return OTHER;
    }
}
