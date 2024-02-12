package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.model.report.*;
import cn.wbnull.hellobill.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表接口
 *
 * @author dukunbiao(null)  2023-01-28
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("report")
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    /**
     * 支出信息报表
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "expend")
    public ResponseModel<Object> expend(@RequestBody @Validated ReportRequestModel request,
                                        BindingResult result) throws Exception {
        super.validate(result);

        return reportService.expend(request);
    }

    /**
     * 收入信息报表
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "income")
    public ResponseModel<Object> income(@RequestBody @Validated ReportRequestModel request,
                                        BindingResult result) throws Exception {
        super.validate(result);

        return reportService.income(request);
    }

    /**
     * 报表查询接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "query")
    public ResponseModel<QueryResponseModel> query(@RequestBody @Validated RequestModel request,
                                                   BindingResult result) throws Exception {
        super.validate(result);

        return reportService.query(request);
    }

    /**
     * 分类报表查询接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "topClass")
    public ResponseModel<TopClassResponseModel> topClass(@RequestBody @Validated TopClassRequestModel request,
                                                         BindingResult result) throws Exception {
        super.validate(result);

        return reportService.topClass(request);
    }

    /**
     * 详情报表查询接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "detail")
    public ResponseModel<DetailResponseModel> detail(@RequestBody @Validated DetailRequestModel request,
                                                     BindingResult result) throws Exception {
        super.validate(result);

        return reportService.detail(request);
    }
}
