package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.model.report.QueryResponseModel;
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
public class ReportService {

    @Autowired
    private ExpendInfoService expendInfoService;

    @Autowired
    private IncomeInfoService incomeInfoService;

    public ResponseModel<QueryResponseModel> query(RequestModel request) throws Exception {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendReportSum(request.getUsername());
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeReportSum(request.getUsername());

        return ResponseModel.success(QueryResponseModel.build(expendInfos, incomeInfos));
    }
}
