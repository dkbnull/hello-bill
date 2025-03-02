package cn.wbnull.hellobill.common.security.component;

import cn.wbnull.hellobill.common.core.constant.ResponseCode;
import cn.wbnull.hellobill.common.core.exception.BusinessException;
import cn.wbnull.hellobill.common.security.config.JwtProperties;
import cn.wbnull.hellobill.common.security.model.TokenModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * JWT 工具类
 *
 * @author null  2024-11-30
 */
@Component
public class JwtTokenProvider {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateToken(TokenModel tokenModel) {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(jwtProperties.getExpireTime());

        return JWT.create()
                .withSubject(JSON.toJSONString(tokenModel))
                .withExpiresAt(localDateTime.toInstant(ZoneOffset.of("+8")))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret()));
    }

    public TokenModel validateToken(String token) {
        String subject = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                .build()
                .verify(token)
                .getSubject();

        return JSONObject.parseObject(subject, TokenModel.class);
    }

    public boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            throw new BusinessException(ResponseCode.INVALID_TOKEN);
        }

        try {
            return JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                    .build()
                    .verify(token)
                    .getExpiresAt()
                    .after(new Date());
        } catch (TokenExpiredException e) {
            throw new BusinessException(ResponseCode.TOKEN_TIME_OUT.getCode(), e.getMessage());
        }
    }
}
