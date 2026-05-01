package cn.wbnull.hellobill.common.security.config;

import cn.wbnull.hellobill.common.security.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @author null
 * @date 2024-11-30
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Configuration
@RequiredArgsConstructor
public class JwtConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index.html", "/**.html", "/css/**", "/js/**", "/img/**", "/lib/**")
                .excludePathPatterns("/**/login");
    }
}
