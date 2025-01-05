package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.common.util.BigDecimalUtils;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.ArrayList;
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

    @Deprecated
    private List<T> reportClass;
    @Deprecated
    private List<ReportSecondClassBean<T>> reportSecondClass;
    @Deprecated
    private JSONArray reportDate;
    @Deprecated
    private JSONArray total;

    private List<String> date;
    private Map<String, String> expendData;
    private Map<String, Map<String, String>> expendClassData;

    public static ReportResponseModel<ExpendInfo> buildExpend(List<String> date,
                                                              List<ExpendInfo> expendInfosDateSum,
                                                              List<ExpendInfo> expendInfosSecond) {
        ReportResponseModel<ExpendInfo> responseModel = new ReportResponseModel<>();
        responseModel.date = date;

        responseModel.expendData = new HashMap<>();
        for (ExpendInfo expendInfo : expendInfosDateSum) {
            responseModel.expendData.put(expendInfo.getRemark(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
        }

        responseModel.expendClassData = new HashMap<>();
        for (ExpendInfo expendInfo : expendInfosSecond) {
            if (responseModel.expendClassData.containsKey(expendInfo.getRemark())) {
                Map<String, String> data = responseModel.expendClassData.get(expendInfo.getRemark());
                data.put(expendInfo.getSecondClass(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
                continue;
            }

            Map<String, String> data = new HashMap<>();
            data.put(expendInfo.getSecondClass(), BigDecimalUtils.format2Decimal(expendInfo.getAmount()));
            responseModel.expendClassData.put(expendInfo.getRemark(), data);
        }

        return responseModel;
    }

    public static ReportResponseModel<IncomeInfo> buildIncome(List<IncomeInfo> incomeInfos,
                                                              List<IncomeInfo> incomeInfosSecond) {
        ReportResponseModel<IncomeInfo> responseModel = new ReportResponseModel<>();
        responseModel.reportClass = incomeInfos;
        responseModel.reportSecondClass = new ArrayList<>();

        for (IncomeInfo incomeInfo : incomeInfosSecond) {
            ReportSecondClassBean<IncomeInfo> reportSecondClassTemp = null;
            for (ReportSecondClassBean<IncomeInfo> reportSecondClass : responseModel.reportSecondClass) {
                if (reportSecondClass.topClass.equals(incomeInfo.getTopClass())) {
                    reportSecondClassTemp = reportSecondClass;
                    break;
                }
            }

            if (reportSecondClassTemp == null) {
                responseModel.reportSecondClass.add(ReportSecondClassBean.build(incomeInfo));
            } else {
                reportSecondClassTemp.getReportClass().add(incomeInfo);
            }
        }

        return responseModel;
    }

    @Data
    static class ReportSecondClassBean<T> {

        private String topClass;
        private List<T> reportClass;

        public static ReportSecondClassBean<ExpendInfo> build(ExpendInfo expendInfo) {
            ReportSecondClassBean<ExpendInfo> reportSecondClass = new ReportSecondClassBean<>();
            reportSecondClass.topClass = expendInfo.getTopClass();
            reportSecondClass.reportClass = new ArrayList<>();
            reportSecondClass.reportClass.add(expendInfo);

            return reportSecondClass;
        }

        public static ReportSecondClassBean<IncomeInfo> build(IncomeInfo expendInfo) {
            ReportSecondClassBean<IncomeInfo> reportSecondClass = new ReportSecondClassBean<>();
            reportSecondClass.topClass = expendInfo.getTopClass();
            reportSecondClass.reportClass = new ArrayList<>();
            reportSecondClass.reportClass.add(expendInfo);

            return reportSecondClass;
        }
    }
}
