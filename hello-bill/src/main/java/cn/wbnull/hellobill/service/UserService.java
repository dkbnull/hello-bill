package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.model.user.ChangePasswordRequestModel;
import cn.wbnull.hellobill.model.user.LoginRequestModel;
import cn.wbnull.hellobill.model.user.LoginResponseModel;

/**
 * 用户接口服务类
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public interface UserService {

    ResponseModel<LoginResponseModel> login(RequestModel<LoginRequestModel> request);

    ResponseModel<Object> changePassword(RequestModel<ChangePasswordRequestModel> request);
}
