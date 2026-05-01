package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.common.response.PageResponse;
import cn.wbnull.hellobill.dto.imp.request.BatchConfirmRequest;
import cn.wbnull.hellobill.dto.imp.request.ConfirmRequest;
import cn.wbnull.hellobill.dto.imp.request.ListRequest;
import cn.wbnull.hellobill.dto.imp.request.UpdateRequest;
import cn.wbnull.hellobill.dto.imp.response.QueryResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 账单导入接口服务类
 *
 * @author null
 * @date 2025-01-25
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
public interface ImportService {

    ApiResponse<Object> billFile(MultipartFile file, String username);

    ApiResponse<PageResponse<QueryResponse>> list(ApiRequest<ListRequest> request);

    ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request);

    ApiResponse<Object> update(ApiRequest<UpdateRequest> request);

    ApiResponse<Object> delete(ApiRequest<DeleteRequest> request);

    ApiResponse<Object> confirm(ApiRequest<ConfirmRequest> request);

    ApiResponse<Object> batchConfirm(ApiRequest<BatchConfirmRequest> request);
}
