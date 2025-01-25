package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.common.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.income.AddRequestModel;
import cn.wbnull.hellobill.model.income.IncomeInfoModel;
import cn.wbnull.hellobill.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收入信息接口服务类
 *
 * @author null  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeInfoService incomeInfoService;

    @Override
    public ResponseModel<List<IncomeInfoModel>> queryList(RequestModel<QueryListRequestModel> request) {
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfos(request.getUsername(), request.getData());
        List<IncomeInfoModel> incomeInfoModelList = BeanUtils.copyPropertyList(incomeInfos, IncomeInfoModel.class);

        return ResponseModel.success(incomeInfoModelList);
    }

    @Override
    public ResponseModel<Object> add(RequestModel<AddRequestModel> request) {
        IncomeInfo incomeInfo = BeanUtils.copyProperties(request.getData(), IncomeInfo.class);
        incomeInfoService.addIncomeInfo(request.getUsername(), incomeInfo);

        return ResponseModel.success("记账成功");
    }

    @Override
    public ResponseModel<IncomeInfoModel> query(RequestModel<QueryRequestModel> request) {
        IncomeInfo incomeInfo = incomeInfoService.getIncomeInfo(request.getData().getId());
        IncomeInfoModel incomeInfoModel = BeanUtils.copyProperties(incomeInfo, IncomeInfoModel.class);
        return ResponseModel.success(incomeInfoModel);
    }

    @Override
    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) {
        IncomeInfo incomeInfo = BeanUtils.copyProperties(request.getData(), IncomeInfo.class);
        incomeInfoService.updateIncomeInfo(incomeInfo);

        return ResponseModel.success("修改成功");
    }

    @Override
    public ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request) {
        incomeInfoService.deleteIncomeInfo(request.getData().getId());

        return ResponseModel.success("删除成功");
    }
}
