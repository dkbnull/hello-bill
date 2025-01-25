package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表查询接口响应参数
 *
 * @author null  2023-01-28
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryResponseModel {

    private List<String> expendData;
    private List<String> incomeData;
    private List<String> date;

    public static QueryResponseModel build(List<ExpendInfo> expendInfos, List<IncomeInfo> incomeInfos) {
        QueryResponseModel responseModel = new QueryResponseModel();
        responseModel.expendData = new ArrayList<>();
        responseModel.incomeData = new ArrayList<>();
        responseModel.date = new ArrayList<>();

        for (ExpendInfo expendInfo : expendInfos) {
            responseModel.expendData.add(expendInfo.getAmount().toString());
            responseModel.date.add(expendInfo.getRemark());
        }

        for (IncomeInfo incomeInfo : incomeInfos) {
            responseModel.incomeData.add(incomeInfo.getAmount().toString());
        }

        return responseModel;
    }
}
