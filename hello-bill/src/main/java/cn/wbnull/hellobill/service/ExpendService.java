package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.AddRequestModel;
import cn.wbnull.hellobill.common.model.expend.InfoRequestModel;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单接口服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ExpendService {

    @Autowired
    private ExpendInfoService expendInfoService;

    public ResponseModel<List<ExpendInfo>> info(InfoRequestModel request) throws Exception {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request);

        return ResponseModel.success(expendInfos);
    }

    public ResponseModel<List<String>> classInfo(RequestModel request) throws Exception {
        List<ClassInfo> classInfos = expendInfoService.getClassInfos();
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
}
