package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2024-11-22
 */
@Getter
@Setter
@TableName("class_info")
public class ClassInfo {

    @TableId(value = "uuid", type = IdType.INPUT)
    private String uuid;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    @TableField("type")
    private String type;

    @TableField("serialNo")
    private Integer serialNo;

    @TableField("status")
    private String status;

    public void analyseInfo() {
        this.type = TypeEnum.getTypeEnum(this.type).getTypeName();
    }
}
