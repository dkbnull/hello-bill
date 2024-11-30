package cn.wbnull.hellobill.common.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt配置
 *
 * @author dukunbiao(null)  2024-11-30
 */
@Data
@Component
@ConfigurationProperties(prefix = "hello-bill.jwt")
public class JwtConfig {

    private String secret;
    private Long expireTime;
}
