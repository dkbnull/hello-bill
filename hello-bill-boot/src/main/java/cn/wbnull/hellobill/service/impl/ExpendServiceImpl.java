package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.constant.ClassType;
import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.param.QueryListParam;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.expend.request.AddRequest;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.expend.request.UpdateRequest;
import cn.wbnull.hellobill.dto.expend.response.QueryResponse;
import cn.wbnull.hellobill.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Service
public class ExpendServiceImpl implements ExpendService {

    @Autowired
    private ExpendInfoService expendInfoService;
    @Autowired
    private ClassInfoService classInfoService;

    @Override
    public ApiResponse<List<QueryResponse>> queryList(ApiRequest<QueryListRequest> request) {
        QueryListParam queryListParam = BeanUtils.copyProperties(request.getData(), QueryListParam.class);
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request.getUsername(), queryListParam);
        List<QueryResponse> queryResponseList = BeanUtils.copyPropertyList(expendInfos, QueryResponse.class);

        return ApiResponse.success(queryResponseList);
    }

    @Override
    public ApiResponse<Object> add(ApiRequest<AddRequest> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        ClassInfo classInfo = classInfoService.getClassInfoBySecondClass(ClassType.EXPEND.getTypeCode(),
                expendInfo.getSecondClass());
        expendInfo.build(request.getUsername(), classInfo.getTopClass());

        expendInfoService.addExpendInfo(expendInfo);

        return ApiResponse.success("记账成功");
    }

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request) {
        ExpendInfo expendInfo = expendInfoService.getExpendInfo(request.getData().getId());
        QueryResponse queryResponse = BeanUtils.copyProperties(expendInfo, QueryResponse.class);

        return ApiResponse.success(queryResponse);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        ClassInfo classInfo = classInfoService.getClassInfoBySecondClass(ClassType.EXPEND.getTypeCode(),
                expendInfo.getSecondClass());
        expendInfo.setTopClass(classInfo.getTopClass());

        expendInfoService.updateExpendInfo(expendInfo);

        return ApiResponse.success("修改成功");
    }

    @Override
    public ApiResponse<Object> delete(ApiRequest<DeleteRequest> request) {
        expendInfoService.deleteExpendInfo(request.getData().getId());

        return ApiResponse.success("删除成功");
    }
}
