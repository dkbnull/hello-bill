package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
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
 * @author null  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassInfoService classInfoService;

    @Override
    public ApiResponse<List<QueryResponse>> query(ApiRequest<QueryRequest> request) {
        List<ClassInfo> classInfoList = classInfoService.getClassInfos(request.getData().getType());
        List<QueryResponse> queryResponseList = BeanUtils.copyPropertyList(classInfoList, QueryResponse.class);
        for (QueryResponse queryResponse : queryResponseList) {
            queryResponse.analyse();
        }

        return ApiResponse.success(queryResponseList);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        UpdateRequest data = request.getData();

        classInfoService.updateClassInfo(data.getUuid(), data.getKey(), data.getValue());
        return ApiResponse.success("分类信息更新成功");
    }

    @Override
    public ApiResponse<List<String>> secondClassQuery(ApiRequest<QueryRequest> request) {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(request.getData().getType());
        List<String> secondClasses = classInfos.stream().map(ClassInfo::getSecondClass).collect(Collectors.toList());

        return ApiResponse.success(secondClasses);
    }

    @Override
    public ApiResponse<List<String>> queryClass(ApiRequest<QueryClassRequest> request) {
        QueryClassRequest data = request.getData();

        List<String> classes;
        if (StringUtils.isEmpty(data.getTopClass())) {
            List<ClassInfo> classInfos = classInfoService.getTopClassInfos(data.getType());
            classes = classInfos.stream().map(ClassInfo::getTopClass).collect(Collectors.toList());
        } else {
            List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(data.getType(), data.getTopClass());
            classes = classInfos.stream().map(ClassInfo::getSecondClass).collect(Collectors.toList());
        }

        return ApiResponse.success(classes);
    }
}
