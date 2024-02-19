package cn.wbnull.hellobill.common.model.user;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户修改密码接口请求参数
 *
 * @author dukunbiao(null)  2024-02-19
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChangePasswordRequestModel extends RequestModel {

    @NotEmpty(message = "oldPassword 不能为空")
    private String oldPassword;

    @NotEmpty(message = "newPassword 不能为空")
    private String newPassword;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
