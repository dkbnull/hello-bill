package cn.wbnull.hellobill.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据源配置
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cn.wbnull.hellobill.db.mapper")
public class DataSourceConfig {

}
