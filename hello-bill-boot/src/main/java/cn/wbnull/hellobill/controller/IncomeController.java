package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.income.request.AddRequest;
import cn.wbnull.hellobill.dto.income.response.QueryResponse;
import cn.wbnull.hellobill.dto.income.request.UpdateRequest;
import cn.wbnull.hellobill.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收入信息接口
 *
 * @author null  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    /**
     * 收入信息列表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryList")
    public ApiResponse<List<QueryResponse>> queryList(@RequestBody @Validated ApiRequest<QueryListRequest> request) {
        return incomeService.queryList(request);
    }

    /**
     * 新增收入信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "add")
    public ApiResponse<Object> add(@RequestBody @Validated ApiRequest<AddRequest> request) {
        return incomeService.add(request);
    }

    /**
     * 收入信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return incomeService.query(request);
    }

    /**
     * 修改收入信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return incomeService.update(request);
    }

    /**
     * 删除收入信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "delete")
    public ApiResponse<Object> delete(@RequestBody @Validated ApiRequest<DeleteRequest> request) {
        return incomeService.delete(request);
    }
}
