package cn.wbnull.hellobill.common.security.model;

import lombok.Data;

/**
 * 用户信息实体
 *
 * @author null
 * @date 2024-11-30
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class TokenModel {

    private String username;

    public static TokenModel build(String username) {
        TokenModel tokenModel = new TokenModel();
        tokenModel.username = username;

        return tokenModel;
    }
}
