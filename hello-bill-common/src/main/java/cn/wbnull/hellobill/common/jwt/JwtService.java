package cn.wbnull.hellobill.common.jwt;

import cn.wbnull.hellobill.common.boot.GlobalException;
import cn.wbnull.hellobill.common.constant.ResponseCodeEnum;
import cn.wbnull.hellobill.common.model.TokenModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Jwt服务
 *
 * @author dukunbiao(null)  2024-11-30
 */
@Service
public class JwtService {

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
            return false;
        }

        try {
            return JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                    .build()
                    .verify(token)
                    .getExpiresAt()
                    .after(new Date());
        } catch (TokenExpiredException e) {
            throw new GlobalException(ResponseCodeEnum.TOKEN_TIME_OUT.getCode(), e.getMessage());
        }
    }
}
