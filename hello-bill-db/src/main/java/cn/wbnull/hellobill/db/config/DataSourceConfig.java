package cn.wbnull.hellobill.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据源配置
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cn.wbnull.hellobill.db.mapper")
public class DataSourceConfig {

}
