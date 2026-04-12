package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.user.request.ChangePasswordRequest;
import cn.wbnull.hellobill.dto.user.request.LoginRequest;
import cn.wbnull.hellobill.dto.user.response.LoginResponse;
import cn.wbnull.hellobill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "login")
    public ApiResponse<LoginResponse> login(@RequestBody @Validated ApiRequest<LoginRequest> request) {
        return userService.login(request);
    }

    @PostMapping(value = "changePassword")
    public ApiResponse<Object> changePassword(@RequestBody @Validated ApiRequest<ChangePasswordRequest> request) {
        return userService.changePassword(request);
    }
}
