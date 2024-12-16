package cn.wbnull.hellobill.common.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局配置
 *
 * @author dukunbiao(null)  2024-11-30
 */
@Configuration
public class JwtConfigurer implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index.html", "/**.html", "/css/**", "/js/**", "/img/**", "/lib/**")
                .excludePathPatterns("/**/login");
    }
}
