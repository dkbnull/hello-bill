package cn.wbnull.hellobill.runner;

import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化资产负债信息
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Component
public class GlobalRunner implements CommandLineRunner {

    @Autowired
    private BalanceService balanceService;

    @Override
    public void run(String... args) throws Exception {
        // 服务启动检查并生成资产负债
        LoggerUtils.info("自动生成资产负债信息 开始");
        balanceService.create();
        LoggerUtils.info("自动生成资产负债信息 完成");
    }
}
