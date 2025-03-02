package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.param.QueryListParam;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.repository.ClassInfoRepository;
import cn.wbnull.hellobill.db.repository.ExpendInfoRepository;
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
    private ExpendInfoRepository expendInfoRepository;
    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Override
    public ApiResponse<List<QueryResponse>> queryList(ApiRequest<QueryListRequest> request) {
        QueryListParam queryListParam = BeanUtils.copyProperties(request.getData(), QueryListParam.class);
        List<ExpendInfo> expendInfos = expendInfoRepository.listByParam(request.getUsername(), queryListParam);
        List<QueryResponse> queryResponses = BeanUtils.copyToList(expendInfos, QueryResponse.class);

        return ApiResponse.success(queryResponses);
    }

    @Override
    public ApiResponse<Object> add(ApiRequest<AddRequest> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        ClassInfo classInfo = classInfoRepository.getByTypeAndSecondClass(ClassTypeEnum.EXPEND.getTypeCode(),
                expendInfo.getSecondClass());
        expendInfo.build(request.getUsername(), classInfo.getTopClass());

        expendInfoRepository.insertExpendInfo(expendInfo);

        return ApiResponse.success("记账成功");
    }

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request) {
        ExpendInfo expendInfo = expendInfoRepository.getById(request.getData().getId());
        QueryResponse queryResponse = BeanUtils.copyProperties(expendInfo, QueryResponse.class);

        return ApiResponse.success(queryResponse);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        ClassInfo classInfo = classInfoRepository.getByTypeAndSecondClass(ClassTypeEnum.EXPEND.getTypeCode(),
                expendInfo.getSecondClass());
        expendInfo.setTopClass(classInfo.getTopClass());

        expendInfoRepository.updateExpendInfo(expendInfo);

        return ApiResponse.success("修改成功");
    }

    @Override
    public ApiResponse<Object> delete(ApiRequest<DeleteRequest> request) {
        expendInfoRepository.deleteById(request.getData().getId());

        return ApiResponse.success("删除成功");
    }
}
