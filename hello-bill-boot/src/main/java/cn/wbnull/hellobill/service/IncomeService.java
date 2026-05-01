package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.ListRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.common.response.PageResponse;
import cn.wbnull.hellobill.dto.income.request.AddRequest;
import cn.wbnull.hellobill.dto.income.request.UpdateRequest;
import cn.wbnull.hellobill.dto.income.response.QueryResponse;

/**
 * 收入信息接口服务类
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
public interface IncomeService {

    ApiResponse<PageResponse<QueryResponse>> list(ApiRequest<ListRequest> request);

    ApiResponse<Object> add(ApiRequest<AddRequest> request);

    ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request);

    ApiResponse<Object> update(ApiRequest<UpdateRequest> request);

    ApiResponse<Object> delete(ApiRequest<DeleteRequest> request);
}
