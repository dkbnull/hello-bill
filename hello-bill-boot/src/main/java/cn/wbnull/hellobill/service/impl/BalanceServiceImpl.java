package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.common.core.util.BigDecimalUtils;
import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.db.entity.BalanceSheet;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.entity.UserInfo;
import cn.wbnull.hellobill.db.repository.BalanceSheetRepository;
import cn.wbnull.hellobill.db.repository.ExpendInfoRepository;
import cn.wbnull.hellobill.db.repository.IncomeInfoRepository;
import cn.wbnull.hellobill.db.repository.UserInfoRepository;
import cn.wbnull.hellobill.dto.balance.response.QueryResponse;
import cn.wbnull.hellobill.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 资产信息接口服务类
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceSheetRepository balanceSheetRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private IncomeInfoRepository incomeInfoRepository;
    @Autowired
    private ExpendInfoRepository expendInfoRepository;

    @Override
    public void create() {
        List<UserInfo> userInfos = userInfoRepository.list();
        for (UserInfo userInfo : userInfos) {
            String username = userInfo.getUsername();
            BalanceSheet balanceSheet = balanceSheetRepository.getLastByUsername(username);
            // 无资产负债信息，从第一条收入、支出明细时间开始计算
            LocalDate balanceDate = null;
            if (balanceSheet == null) {
                IncomeInfo incomeInfo = incomeInfoRepository.getEarliestByUsername(username);
                ExpendInfo expendInfo = expendInfoRepository.getEarliestByUsername(username);
                if (incomeInfo != null) {
                    balanceDate = incomeInfo.getIncomeDate();
                }
                if (expendInfo != null && balanceDate == null) {
                    balanceDate = expendInfo.getExpendTime().toLocalDate();
                }
                if (expendInfo != null) {
                    LocalDate expendDate = expendInfo.getExpendTime().toLocalDate();
                    balanceDate = expendDate.isBefore(balanceDate) ? expendDate : balanceDate;
                }
            } else {
                balanceDate = balanceSheet.getBalanceDate().plusMonths(1);
            }

            if (balanceDate == null) {
                continue;
            }

            balanceDate = balanceDate.withDayOfMonth(1);

            // 未满一月，无需生成资产负债信息
            LocalDate nowDate = LocalDate.now();
            if (balanceDate.plusMonths(1).isAfter(nowDate)) {
                continue;
            }

            // 资产负债日期 ≥ 当前日期，无需再生成资产负债信息
            while (balanceDate.isBefore(nowDate)) {
                IncomeInfo incomeInfo = incomeInfoRepository.getByIncomeDate(username, balanceDate);
                ExpendInfo expendInfo = expendInfoRepository.getByExpendTime(username, balanceDate);

                balanceDate = balanceDate.plusMonths(1);

                BalanceSheet balanceSheetNow = new BalanceSheet();
                balanceSheetNow.setUsername(username);
                balanceSheetNow.setBalanceDate(balanceDate.minusDays(1));
                if (incomeInfo != null) {
                    balanceSheetNow.setIncomeAmount(incomeInfo.getAmount());
                } else {
                    balanceSheetNow.setIncomeAmount(new BigDecimal(0));
                }
                if (expendInfo != null) {
                    balanceSheetNow.setExpendAmount(expendInfo.getAmount());
                } else {
                    balanceSheetNow.setExpendAmount(new BigDecimal(0));
                }
                if (balanceSheet != null) {
                    balanceSheetNow.setBalanceAmount(balanceSheet.getBalanceAmount());
                } else {
                    balanceSheetNow.setBalanceAmount(new BigDecimal(0));
                }

                BigDecimal balanceAmount = balanceSheetNow.getBalanceAmount().add(balanceSheetNow.getIncomeAmount())
                        .subtract(balanceSheetNow.getExpendAmount());
                balanceSheetNow.setBalanceAmount(balanceAmount);

                // 本月收支为0，不保存资产负债信息
                if (BigDecimalUtils.isEqual(balanceSheetNow.getIncomeAmount(), new BigDecimal(0)) &&
                        BigDecimalUtils.isEqual(balanceSheetNow.getExpendAmount(), new BigDecimal(0))) {
                    continue;
                }

                LoggerUtils.info(String.format("新增资产负债信息: %s", balanceSheetNow.getBalanceDate()));

                balanceSheetRepository.save(balanceSheetNow);
                balanceSheet = balanceSheetNow;
            }
        }
    }

    @Override
    public ApiResponse<List<QueryResponse>> query(ApiRequest<Object> request) {
        List<BalanceSheet> balanceSheets = balanceSheetRepository.listByParam(request.getUsername());
        List<QueryResponse> responses = BeanUtils.copyToList(balanceSheets, QueryResponse.class);

        return ApiResponse.success(responses);
    }
}
