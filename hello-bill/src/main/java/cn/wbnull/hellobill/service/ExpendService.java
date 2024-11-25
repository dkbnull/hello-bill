package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.common.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.model.expend.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ExpendService {

    @Autowired
    private ExpendInfoService expendInfoService;

    @Autowired
    private ClassInfoService classInfoService;

    public ResponseModel<List<ExpendInfoModel>> queryList(RequestModel<QueryListRequestModel> request) throws Exception {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request.getUsername(), request.getData());
        List<ExpendInfoModel> expendInfoModelList = BeanUtils.copyPropertyList(expendInfos, ExpendInfoModel.class);

        return ResponseModel.success(expendInfoModelList);
    }

    public ResponseModel<List<String>> classQuery(RequestModel<Object> request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfosByType(TypeEnum.EXPEND.getTypeCode());
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(RequestModel<AddRequestModel> request) throws Exception {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        expendInfoService.addExpendInfo(request.getUsername(), expendInfo);

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<ExpendInfoModel> query(RequestModel<QueryRequestModel> request) throws Exception {
        ExpendInfo expendInfo = expendInfoService.getExpendInfo(request.getData().getId());
        ExpendInfoModel expendInfoModel = BeanUtils.copyProperties(expendInfo, ExpendInfoModel.class);

        return ResponseModel.success(expendInfoModel);
    }

    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) throws Exception {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        expendInfoService.updateExpendInfo(expendInfo);

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request) throws Exception {
        expendInfoService.deleteExpendInfo(request.getUsername(), request.getData().getId());

        return ResponseModel.success("删除成功");
    }
}
