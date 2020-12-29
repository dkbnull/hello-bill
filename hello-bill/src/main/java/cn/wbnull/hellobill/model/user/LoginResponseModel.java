package cn.wbnull.hellobill.model.user;

import cn.wbnull.hellobill.db.entity.UserInfo;
import lombok.Data;

/**
 * 登录接口响应参数
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class LoginResponseModel {

    private String username;

    public static LoginResponseModel build(UserInfo userInfo) {
        LoginResponseModel responseModel = new LoginResponseModel();
        responseModel.username = userInfo.getUsername();

        return responseModel;
    }
}
