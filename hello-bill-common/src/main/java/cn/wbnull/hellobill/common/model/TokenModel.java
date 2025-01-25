package cn.wbnull.hellobill.common.model;

import lombok.Data;

/**
 * Token Model
 *
 * @author null  2024-11-30
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
