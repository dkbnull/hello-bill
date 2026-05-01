package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.exception.BusinessException;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import cn.wbnull.hellobill.db.param.QueryListParam;
import cn.wbnull.hellobill.db.repository.ClassInfoRepository;
import cn.wbnull.hellobill.db.repository.ExpendInfoRepository;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.ListRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.common.response.PageResponse;
import cn.wbnull.hellobill.dto.expend.request.AddRequest;
import cn.wbnull.hellobill.dto.expend.request.UpdateRequest;
import cn.wbnull.hellobill.dto.expend.response.QueryResponse;
import cn.wbnull.hellobill.service.ExpendService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Service
@RequiredArgsConstructor
public class ExpendServiceImpl implements ExpendService {

    private final ExpendInfoMapper expendInfoMapper;
    private final ExpendInfoRepository expendInfoRepository;
    private final ClassInfoRepository classInfoRepository;

    @Override
    public ApiResponse<PageResponse<QueryResponse>> list(ApiRequest<ListRequest> request) {
        ListRequest data = request.getData();
        QueryListParam queryListParam = BeanUtils.copyProperties(data, QueryListParam.class);
        IPage<ExpendInfo> page = expendInfoRepository.pageByParam(request.getUsername(), queryListParam,
                data.getPageNum(), data.getPageSize());
        List<QueryResponse> records = BeanUtils.copyToList(page.getRecords(), QueryResponse.class);
        PageResponse<QueryResponse> response = PageResponse.of(records, page.getTotal(),
                page.getSize(), page.getCurrent());

        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Object> add(ApiRequest<AddRequest> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        ClassInfo classInfo = classInfoRepository.getByTypeAndSecondClass(ClassTypeEnum.EXPEND.getTypeCode(),
                expendInfo.getSecondClass());
        if (classInfo == null) {
            throw new BusinessException("分类信息不存在");
        }
        expendInfo.build(request.getUsername(), classInfo.getTopClass());

        expendInfoMapper.insert(expendInfo);

        return ApiResponse.success("记账成功");
    }

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request) {
        ExpendInfo expendInfo = expendInfoMapper.selectById(request.getData().getId());
        if (expendInfo == null) {
            throw new BusinessException("支出信息不存在");
        }
        QueryResponse queryResponse = BeanUtils.copyProperties(expendInfo, QueryResponse.class);

        return ApiResponse.success(queryResponse);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        ExpendInfo expendInfo = BeanUtils.copyProperties(request.getData(), ExpendInfo.class);
        ClassInfo classInfo = classInfoRepository.getByTypeAndSecondClass(ClassTypeEnum.EXPEND.getTypeCode(),
                expendInfo.getSecondClass());
        if (classInfo == null) {
            throw new BusinessException("分类信息不存在");
        }
        expendInfo.setTopClass(classInfo.getTopClass());

        expendInfoMapper.updateById(expendInfo);

        return ApiResponse.success("修改成功");
    }

    @Override
    public ApiResponse<Object> delete(ApiRequest<DeleteRequest> request) {
        expendInfoMapper.deleteById(request.getData().getId());

        return ApiResponse.success("删除成功");
    }
}
