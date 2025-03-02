package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.param.QueryListParam;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryListRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.income.request.AddRequest;
import cn.wbnull.hellobill.dto.income.request.UpdateRequest;
import cn.wbnull.hellobill.dto.income.response.QueryResponse;
import cn.wbnull.hellobill.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收入信息接口服务类
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeInfoService incomeInfoService;

    @Override
    public ApiResponse<List<QueryResponse>> queryList(ApiRequest<QueryListRequest> request) {
        QueryListParam queryListParam = BeanUtils.copyProperties(request.getData(), QueryListParam.class);
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfos(request.getUsername(), queryListParam);
        List<QueryResponse> queryResponseList = BeanUtils.copyPropertyList(incomeInfos, QueryResponse.class);

        return ApiResponse.success(queryResponseList);
    }

    @Override
    public ApiResponse<Object> add(ApiRequest<AddRequest> request) {
        IncomeInfo incomeInfo = BeanUtils.copyProperties(request.getData(), IncomeInfo.class);
        incomeInfoService.addIncomeInfo(request.getUsername(), incomeInfo);

        return ApiResponse.success("记账成功");
    }

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request) {
        IncomeInfo incomeInfo = incomeInfoService.getIncomeInfo(request.getData().getId());
        QueryResponse queryResponse = BeanUtils.copyProperties(incomeInfo, QueryResponse.class);

        return ApiResponse.success(queryResponse);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        IncomeInfo incomeInfo = BeanUtils.copyProperties(request.getData(), IncomeInfo.class);
        incomeInfoService.updateIncomeInfo(incomeInfo);

        return ApiResponse.success("修改成功");
    }

    @Override
    public ApiResponse<Object> delete(ApiRequest<DeleteRequest> request) {
        incomeInfoService.deleteIncomeInfo(request.getData().getId());

        return ApiResponse.success("删除成功");
    }
}
