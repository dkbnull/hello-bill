package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.model.ResponseModel;
import cn.wbnull.hellobill.model.bill.InfoRequestModel;
import cn.wbnull.hellobill.db.entity.BillInfo;
import cn.wbnull.hellobill.db.service.BillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<BillInfo> billInfos = billInfoService.getBillInfos(request.getUsername(), request.getTopClass(),
                request.getSecondClass(), request.getDetail(), request.getBeginTime(), request.getEndTime());

        return ResponseModel.success(billInfos);
    }
}
