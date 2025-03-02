package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.model.RequestModel;
import cn.wbnull.hellobill.common.core.model.ResponseModel;
import cn.wbnull.hellobill.model.user.ChangePasswordRequestModel;
import cn.wbnull.hellobill.model.user.LoginRequestModel;
import cn.wbnull.hellobill.model.user.LoginResponseModel;

/**
 * 用户接口服务类
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public interface UserService {

    ResponseModel<LoginResponseModel> login(RequestModel<LoginRequestModel> request);

    ResponseModel<Object> changePassword(RequestModel<ChangePasswordRequestModel> request);
}
