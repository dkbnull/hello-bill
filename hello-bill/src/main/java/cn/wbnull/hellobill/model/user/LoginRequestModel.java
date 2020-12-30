package cn.wbnull.hellobill.model.user;

import cn.wbnull.hellobill.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录接口请求参数
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class LoginRequestModel extends RequestModel {

    @NotEmpty(message = "password 不能为空")
    private String password;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
