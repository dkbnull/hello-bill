package cn.wbnull.hellobill.service;

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

    public ResponseModel<List<ExpendInfo>> queryList(QueryListRequestModel request) throws Exception {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request);

        return ResponseModel.success(expendInfos);
    }

    public ResponseModel<List<String>> classQuery(RequestModel request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getClassInfos(ClassInfo.TYPE_EXPEND);
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(AddRequestModel request) throws Exception {
        expendInfoService.addExpendInfo(request);

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<ExpendInfo> query(QueryRequestModel request) throws Exception {
        return ResponseModel.success(expendInfoService.getExpendInfo(request));
    }

    public ResponseModel<Object> update(UpdateRequestModel request) throws Exception {
        expendInfoService.updateExpendInfo(request);

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(DeleteRequestModel request) throws Exception {
        expendInfoService.deleteExpendInfo(request);

        return ResponseModel.success("删除成功");
    }
}
