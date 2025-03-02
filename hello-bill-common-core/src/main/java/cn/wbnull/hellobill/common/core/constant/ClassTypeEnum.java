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

    EXPEND(0, "支出"),
    INCOME(1, "收入"),
    OTHER(2, "其他");

    private final int typeCode;
    private final String typeName;

    ClassTypeEnum(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public static String getTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }

        for (ClassTypeEnum classTypeEnum : ClassTypeEnum.values()) {
            if (classTypeEnum.typeCode == typeCode) {
                return classTypeEnum.getTypeName();
            }
        }

        return null;
    }
}
