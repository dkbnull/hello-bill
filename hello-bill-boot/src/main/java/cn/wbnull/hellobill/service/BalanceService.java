package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.balance.response.QueryResponse;

import java.util.List;

/**
 * 资产信息接口服务类
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
public interface BalanceService {

    void create();

    ApiResponse<List<QueryResponse>> query(ApiRequest<Object> request);
}
