package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.cls.request.QueryClassRequest;
import cn.wbnull.hellobill.dto.cls.request.QueryRequest;
import cn.wbnull.hellobill.dto.cls.request.UpdateRequest;
import cn.wbnull.hellobill.dto.cls.response.QueryResponse;

import java.util.List;

/**
 * 分类信息接口服务类
 *
 * @author null  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
public interface ClassService {

    ApiResponse<List<QueryResponse>> query(ApiRequest<QueryRequest> request);

    ApiResponse<Object> update(ApiRequest<UpdateRequest> request);

    ApiResponse<List<String>> secondClassQuery(ApiRequest<QueryRequest> request);

    ApiResponse<List<String>> queryClass(ApiRequest<QueryClassRequest> request);
}
