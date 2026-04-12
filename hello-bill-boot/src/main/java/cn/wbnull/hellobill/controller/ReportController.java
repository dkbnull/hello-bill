package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.report.request.ExpendClassRequest;
import cn.wbnull.hellobill.dto.report.request.ExpendDetailRequest;
import cn.wbnull.hellobill.dto.report.request.ReportRequest;
import cn.wbnull.hellobill.dto.report.response.QueryResponse;
import cn.wbnull.hellobill.dto.report.response.ReportClassResponse;
import cn.wbnull.hellobill.dto.report.response.ReportDetailResponse;
import cn.wbnull.hellobill.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表接口
 *
 * @author null
 * @date 2023-01-28
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<Object> request) {
        return reportService.query(request);
    }

    @PostMapping(value = "queryNet")
    public ApiResponse<QueryResponse> queryNet(@RequestBody @Validated ApiRequest<Object> request) {
        return reportService.queryNet(request);
    }

    @PostMapping(value = "expend")
    public ApiResponse<Object> expend(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.expend(request);
    }

    @PostMapping(value = "income")
    public ApiResponse<Object> income(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.income(request);
    }

    @PostMapping(value = "expendClass")
    public ApiResponse<ReportClassResponse> expendClass(@RequestBody @Validated ApiRequest<ExpendClassRequest> request) {
        return reportService.expendClass(request);
    }

    @PostMapping(value = "expendDetail")
    public ApiResponse<ReportDetailResponse> expendDetail(@RequestBody @Validated ApiRequest<ExpendDetailRequest> request) {
        return reportService.expendDetail(request);
    }

    @PostMapping(value = "incomeClass")
    public ApiResponse<ReportClassResponse> incomeClass(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.incomeClass(request);
    }

    @PostMapping(value = "incomeDetail")
    public ApiResponse<ReportDetailResponse> incomeDetail(@RequestBody @Validated ApiRequest<ReportRequest> request) {
        return reportService.incomeDetail(request);
    }
}
