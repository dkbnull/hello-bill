package cn.wbnull.hellobill.common.security.interceptor;

import cn.wbnull.hellobill.common.security.component.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 拦截器
 *
 * @author null
 * @date 2024-11-30
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.replace("Bearer ", "");
        }

        return jwtTokenProvider.isTokenExpired(token);
    }
}
