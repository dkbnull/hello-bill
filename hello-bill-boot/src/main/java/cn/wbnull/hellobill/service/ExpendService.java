package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.expend.request.AddRequest;
import cn.wbnull.hellobill.dto.expend.request.UpdateRequest;
import cn.wbnull.hellobill.dto.expend.response.QueryResponse;

import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
public interface ExpendService {

    ApiResponse<List<QueryResponse>> queryList(ApiRequest<QueryListRequest> request);

    ApiResponse<Object> add(ApiRequest<AddRequest> request);

    ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request);

    ApiResponse<Object> update(ApiRequest<UpdateRequest> request);

    ApiResponse<Object> delete(ApiRequest<DeleteRequest> request);
}
