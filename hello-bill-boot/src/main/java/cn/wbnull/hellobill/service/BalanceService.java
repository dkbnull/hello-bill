package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.balance.request.ListRequest;
import cn.wbnull.hellobill.dto.balance.response.QueryResponse;
import cn.wbnull.hellobill.dto.common.response.PageResponse;

/**
 * 资产信息接口服务类
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
public interface BalanceService {

    void create();

    ApiResponse<PageResponse<QueryResponse>> list(ApiRequest<ListRequest> request);
}
