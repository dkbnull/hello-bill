package cn.wbnull.hellobill.db.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2020-12-29
 */
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Serializable pkVal() {
        return this.username;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
