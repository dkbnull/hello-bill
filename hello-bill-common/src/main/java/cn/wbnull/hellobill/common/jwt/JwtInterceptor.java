package cn.wbnull.hellobill.common.jwt;

import cn.wbnull.hellobill.common.boot.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT
 *
 * @author dukunbiao(null)  2024-11-30
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (!jwtService.isTokenExpired(token)) {
            throw new GlobalException("token 不合法或已过期");
        }

        return true;
    }
}
