package cn.wbnull.hellobill.dto.report.response;

import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出详情报表查询接口响应参数
 *
 * @author null
 * @date 2024-02-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class ReportDetailResponse {

    private List<String> secondDetail;
    private List<String> secondAmount;

    public static ReportDetailResponse build(List<ExpendInfo> expendInfos) {
        ReportDetailResponse response = new ReportDetailResponse();
        response.secondDetail = new ArrayList<>();
        response.secondAmount = new ArrayList<>();
        int i = 1;
        for (ExpendInfo expendInfo : expendInfos) {
            if (i > 20) {
                break;
            }

            response.secondDetail.add(expendInfo.getDetail());
            response.secondAmount.add(expendInfo.getAmount().toString());
            i++;
        }

        return response;
    }

    public static ReportDetailResponse buildIncome(List<IncomeInfo> incomeInfos) {
        ReportDetailResponse response = new ReportDetailResponse();
        response.secondDetail = new ArrayList<>();
        response.secondAmount = new ArrayList<>();
        int i = 1;
        for (IncomeInfo incomeInfo : incomeInfos) {
            if (i > 20) {
                break;
            }

            response.secondDetail.add(incomeInfo.getDetail());
            response.secondAmount.add(incomeInfo.getAmount().toString());
            i++;
        }

        return response;
    }
}
