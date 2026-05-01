package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.exception.BusinessException;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import cn.wbnull.hellobill.db.param.QueryListParam;
import cn.wbnull.hellobill.db.repository.IncomeInfoRepository;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.ListRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.common.response.PageResponse;
import cn.wbnull.hellobill.dto.income.request.AddRequest;
import cn.wbnull.hellobill.dto.income.request.UpdateRequest;
import cn.wbnull.hellobill.dto.income.response.QueryResponse;
import cn.wbnull.hellobill.service.IncomeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收入信息接口服务类
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeInfoMapper incomeInfoMapper;
    private final IncomeInfoRepository incomeInfoRepository;

    @Override
    public ApiResponse<PageResponse<QueryResponse>> list(ApiRequest<ListRequest> request) {
        ListRequest data = request.getData();
        QueryListParam queryListParam = BeanUtils.copyProperties(data, QueryListParam.class);
        IPage<IncomeInfo> page = incomeInfoRepository.pageByParam(request.getUsername(), queryListParam,
                data.getPageNum(), data.getPageSize());
        List<QueryResponse> records = BeanUtils.copyToList(page.getRecords(), QueryResponse.class);
        PageResponse<QueryResponse> response = PageResponse.of(records, page.getTotal(),
                page.getSize(), page.getCurrent());

        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Object> add(ApiRequest<AddRequest> request) {
        IncomeInfo incomeInfo = BeanUtils.copyProperties(request.getData(), IncomeInfo.class);
        incomeInfoRepository.insertIncomeInfo(request.getUsername(), incomeInfo);

        return ApiResponse.success("记账成功");
    }

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request) {
        IncomeInfo incomeInfo = incomeInfoMapper.selectById(request.getData().getId());
        if (incomeInfo == null) {
            throw new BusinessException("收入信息不存在");
        }
        QueryResponse queryResponse = BeanUtils.copyProperties(incomeInfo, QueryResponse.class);

        return ApiResponse.success(queryResponse);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        IncomeInfo incomeInfo = BeanUtils.copyProperties(request.getData(), IncomeInfo.class);
        incomeInfoRepository.updateIncomeInfo(incomeInfo);

        return ApiResponse.success("修改成功");
    }

    @Override
    public ApiResponse<Object> delete(ApiRequest<DeleteRequest> request) {
        incomeInfoMapper.deleteById(request.getData().getId());

        return ApiResponse.success("删除成功");
    }
}
