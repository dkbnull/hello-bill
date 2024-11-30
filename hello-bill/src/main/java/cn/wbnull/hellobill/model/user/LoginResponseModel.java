package cn.wbnull.hellobill.model.user;

import lombok.Data;

/**
 * 用户登录接口响应参数
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class LoginResponseModel {

    private String token;
    private String username;

    public static LoginResponseModel build(String token, String username) {
        LoginResponseModel responseModel = new LoginResponseModel();
        responseModel.token = token;
        responseModel.username = username;

        return responseModel;
    }
}
