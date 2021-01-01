package cn.wbnull.hellobill.common.model.user;

import lombok.Data;

/**
 * 用户登录接口响应参数
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class LoginResponseModel {

    private String username;

    public static LoginResponseModel build(String username) {
        LoginResponseModel responseModel = new LoginResponseModel();
        responseModel.username = username;

        return responseModel;
    }
}
