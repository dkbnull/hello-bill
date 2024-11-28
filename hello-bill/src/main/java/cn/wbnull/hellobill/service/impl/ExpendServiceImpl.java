package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.common.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.expend.*;
import cn.wbnull.hellobill.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ExpendServiceImpl implements ExpendService {

    @Autowired
    private ExpendInfoService expendInfoService;

    @Override
    public ResponseModel<List<ExpendInfoModel>> queryList(RequestModel<QueryListRequestModel> request) {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request.getUsername(), request.getData());
        List<ExpendInfoModel> expendInfoModelList = BeanUtils.copyPropertyList(expendInfos, ExpendInfoModel.class);

        return ResponseModel.success(expendInfoModelList);
    }

    @Override
    public ResponseModel<Object> add(RequestModel<AddRequestModel> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        expendInfoService.addExpendInfo(request.getUsername(), expendInfo);

        return ResponseModel.success("记账成功");
    }

    @Override
    public ResponseModel<ExpendInfoModel> query(RequestModel<QueryRequestModel> request) {
        ExpendInfo expendInfo = expendInfoService.getExpendInfo(request.getData().getId());
        ExpendInfoModel expendInfoModel = BeanUtils.copyProperties(expendInfo, ExpendInfoModel.class);

        return ResponseModel.success(expendInfoModel);
    }

    @Override
    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        expendInfoService.updateExpendInfo(expendInfo);

        return ResponseModel.success("修改成功");
    }

    @Override
    public ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request) {
        expendInfoService.deleteExpendInfo(request.getData().getId());

        return ResponseModel.success("删除成功");
    }
}
