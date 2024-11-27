package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.model.user.ChangePasswordRequestModel;
import cn.wbnull.hellobill.model.user.LoginRequestModel;
import cn.wbnull.hellobill.model.user.LoginResponseModel;
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
 * @author dukunbiao(null)  2020-12-29
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
     * @throws Exception
     */
    @PostMapping(value = "login")
    public ResponseModel<LoginResponseModel> login(@RequestBody @Validated RequestModel<LoginRequestModel> request) throws Exception {
        return userService.login(request);
    }

    /**
     * 用户修改密码接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "changePassword")
    public ResponseModel<Object> changePassword(@RequestBody @Validated RequestModel<ChangePasswordRequestModel> request) throws Exception {
        return userService.changePassword(request);
    }
}
