package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.user.request.ChangePasswordRequest;
import cn.wbnull.hellobill.dto.user.request.LoginRequest;
import cn.wbnull.hellobill.dto.user.response.LoginResponse;
import cn.wbnull.hellobill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "login")
    public ApiResponse<LoginResponse> login(@RequestBody @Validated ApiRequest<LoginRequest> request) {
        return userService.login(request);
    }

    /**
     * 用户修改密码接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "changePassword")
    public ApiResponse<Object> changePassword(@RequestBody @Validated ApiRequest<ChangePasswordRequest> request) {
        return userService.changePassword(request);
    }
}
