package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.user.LoginRequestModel;
import cn.wbnull.hellobill.common.model.user.LoginResponseModel;
import cn.wbnull.hellobill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
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
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "login")
    public ResponseModel<LoginResponseModel> login(@RequestBody @Validated LoginRequestModel request,
                                                   BindingResult result) throws Exception {
        super.validate(result);

        return userService.login(request);
    }
}
