package cn.wbnull.hellobill.dto.report.response;

import cn.wbnull.hellobill.common.core.util.BigDecimalUtils;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收入/支出信息报表接口响应参数
 *
 * @author null
 * @date 2021-01-26
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class ReportResponse<T> {

    private List<String> date;
    private Map<String, String> amountData;
    private Map<String, Map<String, String>> classAmountData;

    public static ReportResponse<ExpendInfo> buildExpend(List<String> date,
                                                         List<ExpendInfo> expendInfosDate,
                                                         List<ExpendInfo> expendInfosClass) {
        ReportResponse<ExpendInfo> response = new ReportResponse<>();
        response.date = date;

        response.amountData = new HashMap<>();
        for (ExpendInfo expendInfo : expendInfosDate) {
            response.amountData.put(expendInfo.getRemark(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
        }

        response.classAmountData = new HashMap<>();
        for (ExpendInfo expendInfo : expendInfosClass) {
            if (response.classAmountData.containsKey(expendInfo.getRemark())) {
                Map<String, String> data = response.classAmountData.get(expendInfo.getRemark());
                data.put(expendInfo.getSecondClass(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
                continue;
            }

            Map<String, String> data = new HashMap<>();
            data.put(expendInfo.getSecondClass(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
            response.classAmountData.put(expendInfo.getRemark(), data);
        }

        return response;
    }

    public static ReportResponse<IncomeInfo> buildIncome(List<String> date,
                                                         List<IncomeInfo> incomeInfosDate,
                                                         List<IncomeInfo> incomeInfosClass) {
        ReportResponse<IncomeInfo> response = new ReportResponse<>();
        response.date = date;

        response.amountData = new HashMap<>();
        for (IncomeInfo incomeInfo : incomeInfosDate) {
            response.amountData.put(incomeInfo.getRemark(), BigDecimalUtils.format2Decimal(incomeInfo.getAmount()));
        }

        response.classAmountData = new HashMap<>();
        for (IncomeInfo incomeInfo : incomeInfosClass) {
            if (response.classAmountData.containsKey(incomeInfo.getRemark())) {
                Map<String, String> data = response.classAmountData.get(incomeInfo.getRemark());
                data.put(incomeInfo.getSecondClass(), BigDecimalUtils.format2Decimal(incomeInfo.getAmount()));
                continue;
            }

            Map<String, String> data = new HashMap<>();
            data.put(incomeInfo.getSecondClass(), BigDecimalUtils.format2Decimal(incomeInfo.getAmount()));
            response.classAmountData.put(incomeInfo.getRemark(), data);
        }

        return response;
    }
}
