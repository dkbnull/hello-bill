package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.core.model.RequestModel;
import cn.wbnull.hellobill.common.core.model.ResponseModel;
import cn.wbnull.hellobill.common.core.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.expend.*;

import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author null  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
public interface ExpendService {

    ResponseModel<List<ExpendInfoModel>> queryList(RequestModel<QueryListRequestModel> request);

    ResponseModel<Object> add(RequestModel<AddRequestModel> request);

    ResponseModel<ExpendInfoModel> query(RequestModel<QueryRequestModel> request);

    ResponseModel<Object> update(RequestModel<UpdateRequestModel> request);

    ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request);
}
