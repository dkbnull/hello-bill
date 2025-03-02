package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.imp.request.ConfirmRequest;
import cn.wbnull.hellobill.dto.imp.response.QueryResponse;
import cn.wbnull.hellobill.dto.imp.request.UpdateRequest;
import cn.wbnull.hellobill.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 账单导入接口
 *
 * @author null  2025-01-25
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("import")
public class ImportController {

    @Autowired
    private ImportService importService;

    /**
     * 账单文件导入接口
     *
     * @param file
     * @param username
     * @return
     */
    @PostMapping(value = "billFile")
    public ApiResponse<Object> billFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("username") String username) {
        LoggerUtils.info("billFile", "请求", file.getOriginalFilename());

        ApiResponse<Object> response = importService.billFile(file, username);

        LoggerUtils.info("billFile", "响应", response.toString());

        return response;
    }

    /**
     * 账单明细列表查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryList")
    public ApiResponse<List<QueryResponse>> queryList(@RequestBody @Validated ApiRequest<Object> request) {
        return importService.queryList(request);
    }

    /**
     * 账单信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ApiResponse<QueryResponse> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return importService.query(request);
    }

    /**
     * 修改账单信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return importService.update(request);
    }

    /**
     * 删除账单信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "delete")
    public ApiResponse<Object> delete(@RequestBody @Validated ApiRequest<DeleteRequest> request) {
        return importService.delete(request);
    }

    /**
     * 确认账单信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "confirm")
    public ApiResponse<Object> confirm(@RequestBody @Validated ApiRequest<ConfirmRequest> request) {
        return importService.confirm(request);
    }
}
