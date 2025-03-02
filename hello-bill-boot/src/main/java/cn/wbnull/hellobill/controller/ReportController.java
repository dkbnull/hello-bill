package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.report.request.ExpendClassRequest;
import cn.wbnull.hellobill.dto.report.request.ExpendDetailRequest;
import cn.wbnull.hellobill.dto.report.request.ReportRequest;
import cn.wbnull.hellobill.dto.report.response.ReportClassResponse;
import cn.wbnull.hellobill.dto.report.response.ReportDetailResponse;
import cn.wbnull.hellobill.dto.report.response.QueryResponse;
import cn.wbnull.hellobill.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表接口
 *
 * @author null  2023-01-28
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 总收支报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<Object> request) {
        return reportService.query(request);
    }

    /**
     * 净收支报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryNet")
    public ApiResponse<QueryResponse> queryNet(@RequestBody @Validated ApiRequest<Object> request) {
        return reportService.queryNet(request);
    }

    /**
     * 支出信息报表
     *
     * @param request
     * @return
     */
    @PostMapping(value = "expend")
    public ApiResponse<Object> expend(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.expend(request);
    }

    /**
     * 收入信息报表
     *
     * @param request
     * @return
     */
    @PostMapping(value = "income")
    public ApiResponse<Object> income(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.income(request);
    }

    /**
     * 支出分类报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "expendClass")
    public ApiResponse<ReportClassResponse> expendClass(@RequestBody @Validated ApiRequest<ExpendClassRequest> request) {
        return reportService.expendClass(request);
    }

    /**
     * 支出详情报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "expendDetail")
    public ApiResponse<ReportDetailResponse> expendDetail(@RequestBody @Validated ApiRequest<ExpendDetailRequest> request) {
        return reportService.expendDetail(request);
    }

    /**
     * 收入报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "incomeClass")
    public ApiResponse<ReportClassResponse> incomeClass(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.incomeClass(request);
    }

    /**
     * 支出详情报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "incomeDetail")
    public ApiResponse<ReportDetailResponse> incomeDetail(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.incomeDetail(request);
    }
}
