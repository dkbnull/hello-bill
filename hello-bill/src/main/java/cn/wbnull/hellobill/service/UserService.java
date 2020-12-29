package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.db.entity.UserInfo;
import cn.wbnull.hellobill.db.service.UserInfoService;
import cn.wbnull.hellobill.model.ResponseModel;
import cn.wbnull.hellobill.model.user.LoginRequestModel;
import cn.wbnull.hellobill.model.user.LoginResponseModel;
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

    public ResponseModel<LoginResponseModel> login(LoginRequestModel request) throws Exception {
        UserInfo userInfo = userInfoService.getUserInfo(request.getUsername());
        if (userInfo == null) {
            return ResponseModel.fail("用户不存在");
        }

        if (!userInfo.getPassword().equals(DigestUtils.md5Hex(request.getPassword()).toUpperCase())) {
            return ResponseModel.fail("用户名或密码错误");
        }

        return ResponseModel.success(LoginResponseModel.build(userInfo));
    }
}
