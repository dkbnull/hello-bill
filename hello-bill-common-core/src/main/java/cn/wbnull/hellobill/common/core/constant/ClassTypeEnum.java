package cn.wbnull.hellobill.common.core.constant;

import lombok.Getter;

/**
 * 分类类型枚举
 *
 * @author null
 * @date 2022-01-04
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
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

    public static ClassTypeEnum getClassType(String typeCode) {
        for (ClassTypeEnum classTypeEnum : ClassTypeEnum.values()) {
            if (classTypeEnum.typeCode.equals(typeCode)) {
                return classTypeEnum;
            }
        }

        return OTHER;
    }
}
