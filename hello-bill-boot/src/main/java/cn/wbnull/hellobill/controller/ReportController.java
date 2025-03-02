package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.model.RequestModel;
import cn.wbnull.hellobill.common.core.model.ResponseModel;
import cn.wbnull.hellobill.model.report.*;
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
    public ResponseModel<QueryResponseModel> query(@RequestBody @Validated RequestModel<Object> request) {
        return reportService.query(request);
    }

    /**
     * 净收支报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryNet")
    public ResponseModel<QueryResponseModel> queryNet(@RequestBody @Validated RequestModel<Object> request) {
        return reportService.queryNet(request);
    }

    /**
     * 支出信息报表
     *
     * @param request
     * @return
     */
    @PostMapping(value = "expend")
    public ResponseModel<Object> expend(@RequestBody @Validated RequestModel<ReportRequestModel> request) {
        return reportService.expend(request);
    }

    /**
     * 收入信息报表
     *
     * @param request
     * @return
     */
    @PostMapping(value = "income")
    public ResponseModel<Object> income(@RequestBody @Validated RequestModel<ReportRequestModel> request) {
        return reportService.income(request);
    }

    /**
     * 支出分类报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "expendClass")
    public ResponseModel<ExpendClassResponseModel> expendClass(@RequestBody @Validated RequestModel<ExpendClassRequestModel> request) {
        return reportService.expendClass(request);
    }

    /**
     * 支出详情报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "expendDetail")
    public ResponseModel<ExpendDetailResponseModel> expendDetail(@RequestBody @Validated RequestModel<ExpendDetailRequestModel> request) {
        return reportService.expendDetail(request);
    }

    /**
     * 收入报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "incomeClass")
    public ResponseModel<ExpendClassResponseModel> incomeClass(@RequestBody @Validated RequestModel<ReportRequestModel> request) {
        return reportService.incomeClass(request);
    }

    /**
     * 支出详情报表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "incomeDetail")
    public ResponseModel<ExpendDetailResponseModel> incomeDetail(@RequestBody @Validated RequestModel<ReportRequestModel> request) {
        return reportService.incomeDetail(request);
    }
}
