package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.income.request.AddRequest;
import cn.wbnull.hellobill.dto.income.request.UpdateRequest;
import cn.wbnull.hellobill.dto.income.response.QueryResponse;
import cn.wbnull.hellobill.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收入信息接口
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@RestController
@RequestMapping("income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping(value = "queryList")
    public ApiResponse<List<QueryResponse>> queryList(@RequestBody @Validated ApiRequest<QueryListRequest> request) {
        return incomeService.queryList(request);
    }

    @PostMapping(value = "add")
    public ApiResponse<Object> add(@RequestBody @Validated ApiRequest<AddRequest> request) {
        return incomeService.add(request);
    }

    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return incomeService.query(request);
    }

    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return incomeService.update(request);
    }

    @PostMapping(value = "delete")
    public ApiResponse<Object> delete(@RequestBody @Validated ApiRequest<DeleteRequest> request) {
        return incomeService.delete(request);
    }
}
