package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.model.cls.QueryClassRequestModel;
import cn.wbnull.hellobill.model.cls.QueryRequestModel;
import cn.wbnull.hellobill.model.cls.UpdateRequestModel;

import java.util.List;

/**
 * 分类信息接口服务类
 *
 * @author dukunbiao(null)  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
public interface ClassService {

    ResponseModel<List<ClassInfo>> query(RequestModel<QueryRequestModel> request);

    ResponseModel<Object> update(RequestModel<UpdateRequestModel> request);

    ResponseModel<List<String>> secondClassQuery(RequestModel<QueryRequestModel> request);

    ResponseModel<List<String>> queryClass(RequestModel<QueryClassRequestModel> request);
}
