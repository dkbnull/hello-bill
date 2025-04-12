package cn.wbnull.hellobill.dto.user.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录接口请求参数
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class LoginRequest {

    @NotEmpty(message = "username 不能为空")
    private String username;

    @NotEmpty(message = "password 不能为空")
    private String password;
}
