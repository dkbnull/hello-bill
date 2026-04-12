package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.expend.request.AddRequest;
import cn.wbnull.hellobill.dto.expend.request.UpdateRequest;
import cn.wbnull.hellobill.dto.expend.response.QueryResponse;
import cn.wbnull.hellobill.service.ExpendService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 支出信息接口
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@RestController
@RequestMapping("expend")
@RequiredArgsConstructor
public class ExpendController {

    private final ExpendService expendService;

    @PostMapping(value = "queryList")
    public ApiResponse<List<QueryResponse>> queryList(@RequestBody @Validated ApiRequest<QueryListRequest> request) {
        return expendService.queryList(request);
    }

    @PostMapping(value = "add")
    public ApiResponse<Object> add(@RequestBody @Validated ApiRequest<AddRequest> request) {
        return expendService.add(request);
    }

    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return expendService.query(request);
    }

    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return expendService.update(request);
    }

    @PostMapping(value = "delete")
    public ApiResponse<Object> delete(@RequestBody @Validated ApiRequest<DeleteRequest> request) {
        return expendService.delete(request);
    }
}
