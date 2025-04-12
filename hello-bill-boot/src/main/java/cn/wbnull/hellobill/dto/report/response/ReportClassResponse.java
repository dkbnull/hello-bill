package cn.wbnull.hellobill.dto.report.response;

import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出分类报表查询接口响应参数
 *
 * @author null
 * @date 2024-02-11
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class ReportClassResponse {

    private List<String> secondClass;
    private List<String> secondAmount;

    public static ReportClassResponse build(List<ClassInfo> classInfos, List<ExpendInfo> expendInfos) {
        ReportClassResponse response = new ReportClassResponse();
        response.secondClass = new ArrayList<>();
        response.secondAmount = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            for (ExpendInfo expendInfo : expendInfos) {
                if (!classInfo.getSecondClass().equals(expendInfo.getSecondClass())) {
                    continue;
                }

                response.secondClass.add(expendInfo.getSecondClass());
                response.secondAmount.add(expendInfo.getAmount().toString());
                break;
            }
        }

        return response;
    }

    public static ReportClassResponse buildIncome(List<ClassInfo> classInfos, List<IncomeInfo> incomeInfos) {
        ReportClassResponse response = new ReportClassResponse();
        response.secondClass = new ArrayList<>();
        response.secondAmount = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            for (IncomeInfo incomeInfo : incomeInfos) {
                if (!classInfo.getTopClass().equals(incomeInfo.getTopClass())) {
                    continue;
                }

                response.secondClass.add(incomeInfo.getTopClass());
                response.secondAmount.add(incomeInfo.getAmount().toString());
                break;
            }
        }

        return response;
    }
}
