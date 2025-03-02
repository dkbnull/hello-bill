package cn.wbnull.hellobill.dto.report.response;

import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 报表查询接口响应参数
 *
 * @author null
 * @date 2023-01-28
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class QueryResponse {

    private List<String> expendData;
    private List<String> incomeData;
    private List<String> date;

    public static QueryResponse build(List<ExpendInfo> expendInfos, List<IncomeInfo> incomeInfos) {
        QueryResponse response = new QueryResponse();
        response.expendData = new ArrayList<>();
        response.incomeData = new ArrayList<>();
        response.date = new ArrayList<>();

        for (ExpendInfo expendInfo : expendInfos) {
            response.date.add(expendInfo.getRemark());
        }

        for (IncomeInfo incomeInfo : incomeInfos) {
            if (!response.date.contains(incomeInfo.getRemark())) {
                response.date.add(incomeInfo.getRemark());
            }
        }

        Collections.sort(response.date);

        for (String date : response.date) {
            ExpendInfo expendInfoTemp = null;
            for (ExpendInfo expendInfo : expendInfos) {
                if (expendInfo.getRemark().equals(date)) {
                    expendInfoTemp = expendInfo;
                    break;
                }
            }

            response.expendData.add(expendInfoTemp != null ? expendInfoTemp.getAmount().toString() : "0.00");

            IncomeInfo incomeInfoTemp = null;
            for (IncomeInfo incomeInfo : incomeInfos) {
                if (incomeInfo.getRemark().equals(date)) {
                    incomeInfoTemp = incomeInfo;
                    break;
                }
            }

            response.incomeData.add(incomeInfoTemp != null ? incomeInfoTemp.getAmount().toString() : "0.00");
        }

        return response;
    }
}
