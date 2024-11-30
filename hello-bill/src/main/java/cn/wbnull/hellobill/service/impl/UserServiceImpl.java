package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.jwt.JwtService;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.TokenModel;
import cn.wbnull.hellobill.db.entity.UserInfo;
import cn.wbnull.hellobill.db.service.UserInfoService;
import cn.wbnull.hellobill.model.user.ChangePasswordRequestModel;
import cn.wbnull.hellobill.model.user.LoginRequestModel;
import cn.wbnull.hellobill.model.user.LoginResponseModel;
import cn.wbnull.hellobill.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户接口服务类
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public ResponseModel<LoginResponseModel> login(RequestModel<LoginRequestModel> request) {
        LoginRequestModel data = request.getData();

        UserInfo userInfo = userInfoService.getUserInfo(data.getUsername());
        if (userInfo == null) {
            return ResponseModel.fail("用户名或密码错误");
        }

        String password = DigestUtils.md5Hex(data.getPassword() + userInfo.getSalt()).toUpperCase();
        if (!userInfo.getPassword().equals(password)) {
            return ResponseModel.fail("用户名或密码错误");
        }

        TokenModel tokenModel = TokenModel.build(userInfo.getUsername());
        String token = jwtService.generateToken(tokenModel);

        return ResponseModel.success(LoginResponseModel.build(token, userInfo.getUsername()));
    }

    @Override
    public ResponseModel<Object> changePassword(RequestModel<ChangePasswordRequestModel> request) {
        ChangePasswordRequestModel data = request.getData();

        UserInfo userInfo = userInfoService.getUserInfo(request.getUsername());
        if (userInfo == null) {
            return ResponseModel.fail("用户不存在");
        }

        if (!userInfo.getPassword().equals(DigestUtils.md5Hex(data.getOldPassword()).toUpperCase())) {
            return ResponseModel.fail("原密码错误");
        }

        String password = DigestUtils.md5Hex(data.getNewPassword() + userInfo.getSalt()).toUpperCase();
        userInfoService.updateUserInfo(request.getUsername(), password);

        return ResponseModel.success("密码修改成功");
    }
}
