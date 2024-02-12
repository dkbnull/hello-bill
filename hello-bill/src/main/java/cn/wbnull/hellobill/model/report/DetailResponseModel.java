package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 详情报表查询接口响应参数
 *
 * @author dukunbiao(null)  2024-02-12
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class DetailResponseModel {

    private List<String> secondDetail;
    private List<String> secondAmount;

    public static DetailResponseModel build(List<ExpendInfo> expendInfos) {
        DetailResponseModel responseModel = new DetailResponseModel();
        responseModel.secondDetail = new ArrayList<>();
        responseModel.secondAmount = new ArrayList<>();
        int i = 1;
        for (ExpendInfo expendInfo : expendInfos) {
            if (i > 20) {
                break;
            }

            responseModel.secondDetail.add(expendInfo.getDetail());
            responseModel.secondAmount.add(expendInfo.getAmount());
            i++;
        }

        return responseModel;
    }

    public static DetailResponseModel buildIncome(List<IncomeInfo> incomeInfos) {
        DetailResponseModel responseModel = new DetailResponseModel();
        responseModel.secondDetail = new ArrayList<>();
        responseModel.secondAmount = new ArrayList<>();
        int i = 1;
        for (IncomeInfo incomeInfo : incomeInfos) {
            if (i > 20) {
                break;
            }

            responseModel.secondDetail.add(incomeInfo.getDetail());
            responseModel.secondAmount.add(incomeInfo.getAmount());
            i++;
        }

        return responseModel;
    }
}
