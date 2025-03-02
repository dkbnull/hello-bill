package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.model.RequestModel;
import cn.wbnull.hellobill.common.core.model.ResponseModel;
import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.imp.ConfirmRequestModel;
import cn.wbnull.hellobill.model.imp.ImportBillInfoModel;
import cn.wbnull.hellobill.model.imp.UpdateRequestModel;
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
    public ResponseModel<Object> billFile(@RequestParam("file") MultipartFile file,
                                          @RequestParam("username") String username) {
        LoggerUtils.info("billFile", "请求", file.getOriginalFilename());

        ResponseModel<Object> response = importService.billFile(file, username);

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
    public ResponseModel<List<ImportBillInfoModel>> queryList(@RequestBody @Validated RequestModel<Object> request) {
        return importService.queryList(request);
    }

    /**
     * 账单信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ResponseModel<ImportBillInfoModel> query(@RequestBody @Validated RequestModel<QueryRequestModel> request) {
        return importService.query(request);
    }

    /**
     * 修改账单信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ResponseModel<Object> update(@RequestBody @Validated RequestModel<UpdateRequestModel> request) {
        return importService.update(request);
    }

    /**
     * 删除账单信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "delete")
    public ResponseModel<Object> delete(@RequestBody @Validated RequestModel<DeleteRequestModel> request) {
        return importService.delete(request);
    }

    /**
     * 确认账单信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "confirm")
    public ResponseModel<Object> confirm(@RequestBody @Validated RequestModel<ConfirmRequestModel> request) {
        return importService.confirm(request);
    }
}
