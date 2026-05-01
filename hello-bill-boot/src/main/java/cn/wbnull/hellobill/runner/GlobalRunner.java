package cn.wbnull.hellobill.runner;

import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.service.BalanceService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GlobalRunner implements CommandLineRunner {

    private final BalanceService balanceService;

    @Override
    public void run(String... args) throws Exception {
        LoggerUtils.info("自动生成资产负债信息 开始");
        balanceService.create();
        LoggerUtils.info("自动生成资产负债信息 完成");
    }
}
