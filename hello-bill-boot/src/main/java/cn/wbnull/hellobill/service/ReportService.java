package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.model.RequestModel;
import cn.wbnull.hellobill.common.core.model.ResponseModel;
import cn.wbnull.hellobill.model.report.*;

/**
 * 支出信息接口服务类
 *
 * @author null  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
public interface ReportService {

    ResponseModel<QueryResponseModel> query(RequestModel<Object> request);

    ResponseModel<QueryResponseModel> queryNet(RequestModel<Object> request);

    ResponseModel<Object> expend(RequestModel<ReportRequestModel> request);

    ResponseModel<Object> income(RequestModel<ReportRequestModel> request);

    ResponseModel<ExpendClassResponseModel> expendClass(RequestModel<ExpendClassRequestModel> request);

    ResponseModel<ExpendDetailResponseModel> expendDetail(RequestModel<ExpendDetailRequestModel> request);

    ResponseModel<ExpendClassResponseModel> incomeClass(RequestModel<ReportRequestModel> request);

    ResponseModel<ExpendDetailResponseModel> incomeDetail(RequestModel<ReportRequestModel> request);
}
