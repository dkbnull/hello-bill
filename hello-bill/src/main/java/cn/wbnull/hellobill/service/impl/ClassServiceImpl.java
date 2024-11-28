package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.model.cls.QueryClassRequestModel;
import cn.wbnull.hellobill.model.cls.QueryRequestModel;
import cn.wbnull.hellobill.model.cls.UpdateRequestModel;
import cn.wbnull.hellobill.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类信息接口服务类
 *
 * @author dukunbiao(null)  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassInfoService classInfoService;

    @Override
    public ResponseModel<List<ClassInfo>> query(RequestModel<QueryRequestModel> request) {
        List<ClassInfo> classInfos = classInfoService.getClassInfos(request.getData().getType());
        for (ClassInfo classInfo : classInfos) {
            classInfo.analyseInfo();
        }

        return ResponseModel.success(classInfos);
    }

    @Override
    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) {
        UpdateRequestModel data = request.getData();

        classInfoService.updateClassInfo(data.getUuid(), data.getKey(), data.getValue());
        return ResponseModel.success("分类信息更新成功");
    }

    @Override
    public ResponseModel<List<String>> secondClassQuery(RequestModel<QueryRequestModel> request) {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(request.getData().getType());
        List<String> secondClasses = classInfos.stream().map(ClassInfo::getSecondClass).collect(Collectors.toList());

        return ResponseModel.success(secondClasses);
    }

    @Override
    public ResponseModel<List<String>> queryClass(RequestModel<QueryClassRequestModel> request) {
        QueryClassRequestModel data = request.getData();

        List<String> classes;
        if (StringUtils.isEmpty(data.getTopClass())) {
            List<ClassInfo> classInfos = classInfoService.getTopClassInfos(data.getType());
            classes = classInfos.stream().map(ClassInfo::getTopClass).collect(Collectors.toList());
        } else {
            List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(data.getType(), data.getTopClass());
            classes = classInfos.stream().map(ClassInfo::getSecondClass).collect(Collectors.toList());
        }

        return ResponseModel.success(classes);
    }
}
