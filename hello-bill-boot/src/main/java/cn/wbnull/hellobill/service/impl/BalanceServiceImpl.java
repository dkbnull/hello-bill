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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private final BalanceSheetRepository balanceSheetRepository;
    private final UserInfoRepository userInfoRepository;
    private final IncomeInfoRepository incomeInfoRepository;
    private final ExpendInfoRepository expendInfoRepository;

    @Override
    public void create() {
        List<UserInfo> userInfos = userInfoRepository.list();
        for (UserInfo userInfo : userInfos) {
            processUserBalance(userInfo.getUsername());
        }
    }

    private void processUserBalance(String username) {
        BalanceSheet lastSheet = balanceSheetRepository.getLastByUsername(username);
        LocalDate balanceDate = determineStartDate(username, lastSheet);

        if (balanceDate == null) {
            return;
        }

        balanceDate = balanceDate.withDayOfMonth(1);

        // 未满一月，无需生成资产负债信息
        LocalDate nowDate = LocalDate.now();

        if (balanceDate.plusMonths(1).isAfter(nowDate)) {
            return;
        }

        // 资产负债日期 ≥ 当前日期，无需再生成资产负债信息
        while (balanceDate.isBefore(nowDate)) {
            IncomeInfo incomeInfo = incomeInfoRepository.getByIncomeDate(username, balanceDate);
            ExpendInfo expendInfo = expendInfoRepository.getByExpendTime(username, balanceDate);

            balanceDate = balanceDate.plusMonths(1);

            BalanceSheet currentSheet = buildBalanceSheet(username, balanceDate.minusDays(1),
                    incomeInfo, expendInfo, lastSheet);

            // 本月收支为0，不保存资产负债信息
            if (BigDecimalUtils.isEqual(currentSheet.getIncomeAmount(), ZERO) &&
                    BigDecimalUtils.isEqual(currentSheet.getExpendAmount(), ZERO)) {
                continue;
            }

            LoggerUtils.info(String.format("新增资产负债信息: %s", currentSheet.getBalanceDate()));

            balanceSheetRepository.save(currentSheet);
            lastSheet = currentSheet;
        }
    }

    private LocalDate determineStartDate(String username, BalanceSheet lastSheet) {
        if (lastSheet != null) {
            return lastSheet.getBalanceDate().plusMonths(1);
        }

        // 无资产负债信息，从第一条收入、支出明细时间开始计算
        IncomeInfo earliestIncome = incomeInfoRepository.getEarliestByUsername(username);
        ExpendInfo earliestExpend = expendInfoRepository.getEarliestByUsername(username);

        LocalDate incomeDate = earliestIncome != null ? earliestIncome.getIncomeDate() : null;
        LocalDate expendDate = earliestExpend != null ? earliestExpend.getExpendTime().toLocalDate() : null;

        if (incomeDate == null) {
            return expendDate;
        }
        if (expendDate == null) {
            return incomeDate;
        }
        return expendDate.isBefore(incomeDate) ? expendDate : incomeDate;
    }

    private BalanceSheet buildBalanceSheet(String username, LocalDate balanceDate,
                                           IncomeInfo incomeInfo, ExpendInfo expendInfo,
                                           BalanceSheet lastSheet) {
        BalanceSheet sheet = new BalanceSheet();
        sheet.setUsername(username);
        sheet.setBalanceDate(balanceDate);
        sheet.setIncomeAmount(incomeInfo != null ? incomeInfo.getAmount() : ZERO);
        sheet.setExpendAmount(expendInfo != null ? expendInfo.getAmount() : ZERO);
        sheet.setBalanceAmount(lastSheet != null ? lastSheet.getBalanceAmount() : ZERO);

        BigDecimal balanceAmount = sheet.getBalanceAmount().add(sheet.getIncomeAmount())
                .subtract(sheet.getExpendAmount());
        sheet.setBalanceAmount(balanceAmount);

        return sheet;
    }

    @Override
    public ApiResponse<List<QueryResponse>> query(ApiRequest<Object> request) {
        List<BalanceSheet> balanceSheets = balanceSheetRepository.listByParam(request.getUsername());
        List<QueryResponse> responses = BeanUtils.copyToList(balanceSheets, QueryResponse.class);

        return ApiResponse.success(responses);
    }
}
