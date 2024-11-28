package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.model.report.*;

/**
 * 支出信息接口服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
public interface ReportService {

    ResponseModel<Object> expend(RequestModel<ReportRequestModel> request);

    ResponseModel<Object> income(RequestModel<ReportRequestModel> request);

    ResponseModel<QueryResponseModel> query(RequestModel<Object> request);

    ResponseModel<ExpendClassResponseModel> expendClass(RequestModel<ExpendClassRequestModel> request);

    ResponseModel<ExpendDetailResponseModel> expendDetail(RequestModel<ExpendDetailRequestModel> request);

    ResponseModel<ExpendClassResponseModel> incomeClass(RequestModel<ReportRequestModel> request);

    ResponseModel<ExpendDetailResponseModel> incomeDetail(RequestModel<ReportRequestModel> request);
}
