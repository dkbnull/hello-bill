package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.income.AddRequestModel;
import cn.wbnull.hellobill.model.income.IncomeInfoModel;
import cn.wbnull.hellobill.model.income.UpdateRequestModel;

import java.util.List;

/**
 * 收入信息接口服务类
 *
 * @author null  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
public interface IncomeService {

    ResponseModel<List<IncomeInfoModel>> queryList(RequestModel<QueryListRequestModel> request);

    ResponseModel<Object> add(RequestModel<AddRequestModel> request);

    ResponseModel<IncomeInfoModel> query(RequestModel<QueryRequestModel> request);

    ResponseModel<Object> update(RequestModel<UpdateRequestModel> request);

    ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request);
}
