package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.report.request.ExpendClassRequest;
import cn.wbnull.hellobill.dto.report.request.ExpendDetailRequest;
import cn.wbnull.hellobill.dto.report.request.ReportRequest;
import cn.wbnull.hellobill.dto.report.response.ReportClassResponse;
import cn.wbnull.hellobill.dto.report.response.ReportDetailResponse;
import cn.wbnull.hellobill.dto.report.response.QueryResponse;

/**
 * 支出信息接口服务类
 *
 * @author null  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
public interface ReportService {

    ApiResponse<QueryResponse> query(ApiRequest<Object> request);

    ApiResponse<QueryResponse> queryNet(ApiRequest<Object> request);

    ApiResponse<Object> expend(ApiRequest<ReportRequest> request);

    ApiResponse<Object> income(ApiRequest<ReportRequest> request);

    ApiResponse<ReportClassResponse> expendClass(ApiRequest<ExpendClassRequest> request);

    ApiResponse<ReportDetailResponse> expendDetail(ApiRequest<ExpendDetailRequest> request);

    ApiResponse<ReportClassResponse> incomeClass(ApiRequest<ReportRequest> request);

    ApiResponse<ReportDetailResponse> incomeDetail(ApiRequest<ReportRequest> request);
}
