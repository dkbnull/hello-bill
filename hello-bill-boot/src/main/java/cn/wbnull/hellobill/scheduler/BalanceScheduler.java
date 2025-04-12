package cn.wbnull.hellobill.scheduler;

import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 资产负债信息定时任务
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@EnableScheduling
@Component
public class BalanceScheduler {

    @Autowired
    private BalanceService balanceService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void create() {
        // 每月定期生成资产负债
        LoggerUtils.info("定时生成资产负债信息 开始");
        balanceService.create();
        LoggerUtils.info("定时生成资产负债信息 完成");
    }
}
