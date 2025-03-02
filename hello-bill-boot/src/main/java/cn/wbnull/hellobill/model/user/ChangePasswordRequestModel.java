package cn.wbnull.hellobill.model.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户修改密码接口请求参数
 *
 * @author null  2024-02-19
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ChangePasswordRequestModel {

    @NotEmpty(message = "oldPassword 不能为空")
    private String oldPassword;

    @NotEmpty(message = "newPassword 不能为空")
    private String newPassword;
}
