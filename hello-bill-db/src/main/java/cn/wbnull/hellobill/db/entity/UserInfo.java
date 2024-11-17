package cn.wbnull.hellobill.db.entity;

import com.alibaba.fastjson.JSON;
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
 * @since 2020-12-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String username;

    private String password;

    @Override
    protected Serializable pkVal() {
        return this.username;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
