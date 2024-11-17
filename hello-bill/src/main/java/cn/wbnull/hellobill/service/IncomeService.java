package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryListRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.model.income.IncomeInfoModel;
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

    public ResponseModel<List<IncomeInfoModel>> queryList(RequestModel<QueryListRequestModel> request) throws Exception {
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfos(request.getUsername(), request.getData());
        List<IncomeInfoModel> incomeInfoModelList = BeanUtils.copyPropertyList(incomeInfos, IncomeInfoModel.class);

        return ResponseModel.success(incomeInfoModelList);
    }

    public ResponseModel<List<String>> classQuery(RequestModel<Object> request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfosByType(TypeEnum.INCOME.getTypeCode());
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(RequestModel<AddRequestModel> request) throws Exception {
        incomeInfoService.addIncomeInfo(request.getUsername(), request.getData());

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<IncomeInfoModel> query(RequestModel<QueryRequestModel> request) throws Exception {
        IncomeInfo incomeInfo = incomeInfoService.getIncomeInfo(request.getData());
        IncomeInfoModel incomeInfoModel = BeanUtils.copyProperties(incomeInfo, IncomeInfoModel.class);
        return ResponseModel.success(incomeInfoModel);
    }

    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) throws Exception {
        incomeInfoService.updateIncomeInfo(request.getData());

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request) throws Exception {
        incomeInfoService.deleteIncomeInfo(request.getUsername(), request.getData());

        return ResponseModel.success("删除成功");
    }
}
