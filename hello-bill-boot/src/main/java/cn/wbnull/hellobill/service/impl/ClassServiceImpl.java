package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.repository.ClassInfoRepository;
import cn.wbnull.hellobill.dto.cls.request.QueryClassRequest;
import cn.wbnull.hellobill.dto.cls.request.QueryRequest;
import cn.wbnull.hellobill.dto.cls.request.UpdateRequest;
import cn.wbnull.hellobill.dto.cls.response.QueryResponse;
import cn.wbnull.hellobill.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类信息接口服务类
 *
 * @author null
 * @date 2022-01-04
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Override
    public ApiResponse<List<QueryResponse>> query(ApiRequest<QueryRequest> request) {
        List<ClassInfo> classInfos = classInfoRepository.listByType(request.getData().getType());
        List<QueryResponse> queryResponses = BeanUtils.copyToList(classInfos, QueryResponse.class);
        for (QueryResponse queryResponse : queryResponses) {
            queryResponse.setTypeName(ClassTypeEnum.getTypeName(queryResponse.getType()));
        }

        return ApiResponse.success(queryResponses);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        UpdateRequest data = request.getData();

        if ("serialNo".equals(data.getKey())) {
            classInfoRepository.updateByUuid(data.getUuid(), "serial_no", data.getValue());
        } else if ("status".equals(data.getKey())) {
            classInfoRepository.updateByUuid(data.getUuid(), "status", data.getValue());
        }

        return ApiResponse.success("分类信息更新成功");
    }

    @Override
    public ApiResponse<List<String>> secondClassQuery(ApiRequest<QueryRequest> request) {
        List<ClassInfo> classInfos = classInfoRepository.listSecondByType(request.getData().getType());
        List<String> secondClasses = classInfos.stream().map(ClassInfo::getSecondClass)
                .collect(Collectors.toList());

        return ApiResponse.success(secondClasses);
    }

    @Override
    public ApiResponse<List<String>> queryClass(ApiRequest<QueryClassRequest> request) {
        QueryClassRequest data = request.getData();

        List<String> classes;
        if (StringUtils.isEmpty(data.getTopClass())) {
            List<ClassInfo> classInfos = classInfoRepository.listTopByType(data.getType());
            classes = classInfos.stream().map(ClassInfo::getTopClass).collect(Collectors.toList());
        } else {
            List<ClassInfo> classInfos = classInfoRepository.listSecondByTypeAndTop(
                    data.getType(), data.getTopClass());
            classes = classInfos.stream().map(ClassInfo::getSecondClass).collect(Collectors.toList());
        }

        return ApiResponse.success(classes);
    }
}
