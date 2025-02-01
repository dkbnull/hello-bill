package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.imp.ConfirmRequestModel;
import cn.wbnull.hellobill.model.imp.ImportBillInfoModel;
import cn.wbnull.hellobill.model.imp.UpdateRequestModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 账单导入接口服务类
 *
 * @author null  2025-01-25
 * https://github.com/dkbnull/HelloBill
 */
public interface ImportService {

    ResponseModel<Object> billFile(MultipartFile file, String username);

    ResponseModel<List<ImportBillInfoModel>> queryList(RequestModel<Object> request);

    ResponseModel<ImportBillInfoModel> query(RequestModel<QueryRequestModel> request);

    ResponseModel<Object> update(RequestModel<UpdateRequestModel> request);

    ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request);

    ResponseModel<Object> confirm(RequestModel<ConfirmRequestModel> request);
}
