package cn.wbnull.hellobill.dto.user.response;

import lombok.Data;

/**
 * 用户登录接口响应参数
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class LoginResponse {

    private String token;
    private String username;

    public static LoginResponse build(String token, String username) {
        LoginResponse response = new LoginResponse();
        response.token = token;
        response.username = username;

        return response;
    }
}
