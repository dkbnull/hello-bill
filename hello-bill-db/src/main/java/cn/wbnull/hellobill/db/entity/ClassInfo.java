package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2020-12-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClassInfo extends Model<ClassInfo> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    private String type;

    @TableField("serialNo")
    private Integer serialNo;

    private String status;

    public void analyseInfo() {
        this.type = TypeEnum.getTypeEnum(this.type).getTypeName();
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
