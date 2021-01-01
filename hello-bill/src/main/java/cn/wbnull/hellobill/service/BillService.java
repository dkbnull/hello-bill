package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.bill.AddRequestModel;
import cn.wbnull.hellobill.common.model.bill.InfoRequestModel;
import cn.wbnull.hellobill.db.entity.BillInfo;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.service.BillInfoService;
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
public class BillService {

    @Autowired
    private BillInfoService billInfoService;

    public ResponseModel<List<BillInfo>> info(InfoRequestModel request) throws Exception {
        List<BillInfo> billInfos = billInfoService.getBillInfos(request);

        return ResponseModel.success(billInfos);
    }

    public ResponseModel<List<String>> classInfo(RequestModel request) throws Exception {
        List<ClassInfo> classInfos = billInfoService.getClassInfos();
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(AddRequestModel request) throws Exception {
        billInfoService.addBillInfo(request);

        return ResponseModel.success("记账成功");
    }
}
