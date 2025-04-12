package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.expend.request.AddRequest;
import cn.wbnull.hellobill.dto.expend.request.UpdateRequest;
import cn.wbnull.hellobill.dto.expend.response.QueryResponse;
import cn.wbnull.hellobill.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
@RequestMapping("expend")
public class ExpendController {

    @Autowired
    private ExpendService expendService;

    /**
     * 支出信息明细查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryList")
    public ApiResponse<List<QueryResponse>> queryList(@RequestBody @Validated ApiRequest<QueryListRequest> request) {
        return expendService.queryList(request);
    }

    /**
     * 新增支出信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "add")
    public ApiResponse<Object> add(@RequestBody @Validated ApiRequest<AddRequest> request) {
        return expendService.add(request);
    }

    /**
     * 支出信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return expendService.query(request);
    }

    /**
     * 修改支出信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return expendService.update(request);
    }

    /**
     * 删除支出信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "delete")
    public ApiResponse<Object> delete(@RequestBody @Validated ApiRequest<DeleteRequest> request) {
        return expendService.delete(request);
    }
}
