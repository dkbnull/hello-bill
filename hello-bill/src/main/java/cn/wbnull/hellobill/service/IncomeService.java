package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryListRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 收入信息接口服务类
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class IncomeService {

    @Autowired
    private IncomeInfoService incomeInfoService;

    @Autowired
    private ClassInfoService classInfoService;

    public ResponseModel<List<IncomeInfo>> queryList(QueryListRequestModel request) throws Exception {
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfos(request);

        return ResponseModel.success(incomeInfos);
    }

    public ResponseModel<List<String>> classQuery(RequestModel request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfosByType(TypeEnum.INCOME.getTypeCode());
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(AddRequestModel request) throws Exception {
        incomeInfoService.addIncomeInfo(request);

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<IncomeInfo> query(QueryRequestModel request) throws Exception {
        return ResponseModel.success(incomeInfoService.getIncomeInfo(request));
    }

    public ResponseModel<Object> update(UpdateRequestModel request) throws Exception {
        incomeInfoService.updateIncomeInfo(request);

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(DeleteRequestModel request) throws Exception {
        incomeInfoService.deleteIncomeInfo(request);

        return ResponseModel.success("删除成功");
    }
}
