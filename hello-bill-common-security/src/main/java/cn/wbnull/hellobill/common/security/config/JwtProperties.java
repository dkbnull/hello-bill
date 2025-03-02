package cn.wbnull.hellobill.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置
 *
 * @author null
 * @date 2024-11-30
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private Long expireTime;
}
