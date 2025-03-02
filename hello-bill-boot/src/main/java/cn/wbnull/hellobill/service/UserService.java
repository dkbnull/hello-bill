package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.user.request.ChangePasswordRequest;
import cn.wbnull.hellobill.dto.user.request.LoginRequest;
import cn.wbnull.hellobill.dto.user.response.LoginResponse;

/**
 * 用户接口服务类
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public interface UserService {

    ApiResponse<LoginResponse> login(ApiRequest<LoginRequest> request);

    ApiResponse<Object> changePassword(ApiRequest<ChangePasswordRequest> request);
}
