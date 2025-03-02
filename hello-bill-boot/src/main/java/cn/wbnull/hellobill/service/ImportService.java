package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.imp.request.ConfirmRequest;
import cn.wbnull.hellobill.dto.imp.response.QueryResponse;
import cn.wbnull.hellobill.dto.imp.request.UpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 账单导入接口服务类
 *
 * @author null  2025-01-25
 * https://github.com/dkbnull/HelloBill
 */
public interface ImportService {

    ApiResponse<Object> billFile(MultipartFile file, String username);

    ApiResponse<List<QueryResponse>> queryList(ApiRequest<Object> request);

    ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request);

    ApiResponse<Object> update(ApiRequest<UpdateRequest> request);

    ApiResponse<Object> delete(ApiRequest<DeleteRequest> request);

    ApiResponse<Object> confirm(ApiRequest<ConfirmRequest> request);
}
