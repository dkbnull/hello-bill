package cn.wbnull.hellobill.common.core.constant;

import lombok.Getter;

/**
 * 分类类型枚举
 *
 * @author null  2022-01-04
 */
@Getter
public enum ClassType {

    EXPEND("0", "支出"),
    INCOME("1", "收入"),
    OTHER("", "其他");

    private final String typeCode;
    private final String typeName;

    ClassType(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public static ClassType getClassType(String typeCode) {
        for (ClassType classType : ClassType.values()) {
            if (classType.typeCode.equals(typeCode)) {
                return classType;
            }
        }

        return OTHER;
    }
}
