package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.common.util.BigDecimalUtils;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支出、收入信息报表接口响应参数
 *
 * @author dukunbiao(null)  2021-01-26
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ReportResponseModel<T> {

    private List<String> date;
    private Map<String, String> amountData;
    private Map<String, Map<String, String>> classAmountData;

    public static ReportResponseModel<ExpendInfo> buildExpend(List<String> date,
                                                              List<ExpendInfo> expendInfosDate,
                                                              List<ExpendInfo> expendInfosClass) {
        ReportResponseModel<ExpendInfo> responseModel = new ReportResponseModel<>();
        responseModel.date = date;

        responseModel.amountData = new HashMap<>();
        for (ExpendInfo expendInfo : expendInfosDate) {
            responseModel.amountData.put(expendInfo.getRemark(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
        }

        responseModel.classAmountData = new HashMap<>();
        for (ExpendInfo expendInfo : expendInfosClass) {
            if (responseModel.classAmountData.containsKey(expendInfo.getRemark())) {
                Map<String, String> data = responseModel.classAmountData.get(expendInfo.getRemark());
                data.put(expendInfo.getSecondClass(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
                continue;
            }

            Map<String, String> data = new HashMap<>();
            data.put(expendInfo.getSecondClass(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
            responseModel.classAmountData.put(expendInfo.getRemark(), data);
        }

        return responseModel;
    }

    public static ReportResponseModel<IncomeInfo> buildIncome(List<String> date,
                                                              List<IncomeInfo> incomeInfosDate,
                                                              List<IncomeInfo> incomeInfosClass) {
        ReportResponseModel<IncomeInfo> responseModel = new ReportResponseModel<>();
        responseModel.date = date;

        responseModel.amountData = new HashMap<>();
        for (IncomeInfo incomeInfo : incomeInfosDate) {
            responseModel.amountData.put(incomeInfo.getRemark(), BigDecimalUtils.format2Decimal(incomeInfo.getAmount()));
        }

        responseModel.classAmountData = new HashMap<>();
        for (IncomeInfo incomeInfo : incomeInfosClass) {
            if (responseModel.classAmountData.containsKey(incomeInfo.getRemark())) {
                Map<String, String> data = responseModel.classAmountData.get(incomeInfo.getRemark());
                data.put(incomeInfo.getSecondClass(), BigDecimalUtils.format2Decimal(incomeInfo.getAmount()));
                continue;
            }

            Map<String, String> data = new HashMap<>();
            data.put(incomeInfo.getSecondClass(), BigDecimalUtils.format2Decimal(incomeInfo.getAmount()));
            responseModel.classAmountData.put(incomeInfo.getRemark(), data);
        }

        return responseModel;
    }
}
