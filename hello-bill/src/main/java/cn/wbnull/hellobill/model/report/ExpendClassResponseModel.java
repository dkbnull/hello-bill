package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出分类报表查询接口响应参数
 *
 * @author dukunbiao(null)  2024-02-11
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ExpendClassResponseModel {

    private List<String> secondClass;
    private List<String> secondAmount;

    public static ExpendClassResponseModel build(List<ExpendInfo> expendInfos) {
        ExpendClassResponseModel responseModel = new ExpendClassResponseModel();
        responseModel.secondClass = new ArrayList<>();
        responseModel.secondAmount = new ArrayList<>();
        for (ExpendInfo expendInfo : expendInfos) {
            responseModel.secondClass.add(expendInfo.getSecondClass());
            responseModel.secondAmount.add(expendInfo.getAmount());
        }

        return responseModel;
    }

    public static ExpendClassResponseModel buildIncome(List<IncomeInfo> incomeInfos) {
        ExpendClassResponseModel responseModel = new ExpendClassResponseModel();
        responseModel.secondClass = new ArrayList<>();
        responseModel.secondAmount = new ArrayList<>();
        for (IncomeInfo incomeInfo : incomeInfos) {
            responseModel.secondClass.add(incomeInfo.getTopClass());
            responseModel.secondAmount.add(incomeInfo.getAmount());
        }

        return responseModel;
    }
}
