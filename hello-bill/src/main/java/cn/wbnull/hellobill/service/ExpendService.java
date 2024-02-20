package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.*;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
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

    public ResponseModel<List<ExpendInfo>> queryList(RequestModel<QueryListRequestModel> request) throws Exception {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request.getUsername(), request.getData());

        return ResponseModel.success(expendInfos);
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
        expendInfoService.addExpendInfo(request.getUsername(), request.getData());

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<ExpendInfo> query(RequestModel<QueryRequestModel> request) throws Exception {
        return ResponseModel.success(expendInfoService.getExpendInfo(request.getData()));
    }

    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) throws Exception {
        expendInfoService.updateExpendInfo(request.getData());

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request) throws Exception {
        expendInfoService.deleteExpendInfo(request.getUsername(), request.getData());

        return ResponseModel.success("删除成功");
    }
}
