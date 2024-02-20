package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.user.ChangePasswordRequestModel;
import cn.wbnull.hellobill.common.model.user.LoginRequestModel;
import cn.wbnull.hellobill.common.model.user.LoginResponseModel;
import cn.wbnull.hellobill.db.entity.UserInfo;
import cn.wbnull.hellobill.db.service.UserInfoService;
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
public class UserService {

    @Autowired
    private UserInfoService userInfoService;

    public ResponseModel<LoginResponseModel> login(RequestModel<LoginRequestModel> request) throws Exception {
        LoginRequestModel data = request.getData();

        UserInfo userInfo = userInfoService.getUserInfo(data.getUsername());
        if (userInfo == null) {
            return ResponseModel.fail("用户不存在");
        }

        if (!userInfo.getPassword().equals(DigestUtils.md5Hex(data.getPassword()).toUpperCase())) {
            return ResponseModel.fail("用户名或密码错误");
        }

        return ResponseModel.success(LoginResponseModel.build(userInfo.getUsername()));
    }

    public ResponseModel<Object> changePassword(RequestModel<ChangePasswordRequestModel> request) throws Exception {
        ChangePasswordRequestModel data = request.getData();

        UserInfo userInfo = userInfoService.getUserInfo(request.getUsername());
        if (userInfo == null) {
            return ResponseModel.fail("用户不存在");
        }

        if (!userInfo.getPassword().equals(DigestUtils.md5Hex(data.getOldPassword()).toUpperCase())) {
            return ResponseModel.fail("原密码错误");
        }

        userInfoService.updateUserInfo(request.getUsername(), data.getNewPassword());

        return ResponseModel.success("密码修改成功");
    }
}
