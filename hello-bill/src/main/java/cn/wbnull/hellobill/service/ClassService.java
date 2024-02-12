package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.cls.QueryRequestModel;
import cn.wbnull.hellobill.common.model.cls.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.model.clazz.ClassRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类信息接口服务类
 *
 * @author dukunbiao(null)  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ClassService {

    @Autowired
    private ClassInfoService classInfoService;

    public ResponseModel<List<ClassInfo>> query(QueryRequestModel request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getClassInfos(request.getType());
        for (ClassInfo classInfo : classInfos) {
            classInfo.analyseInfo();
        }

        return ResponseModel.success(classInfos);
    }

    public ResponseModel<Object> update(UpdateRequestModel request) throws Exception {
        classInfoService.updateClassInfo(request.getUuid(), request.getKey(), request.getValue());
        return ResponseModel.success("分类信息更新成功");
    }

    public ResponseModel<List<String>> queryClass(ClassRequestModel request) throws Exception {
        List<String> classes = new ArrayList<>();
        if (StringUtils.isEmpty(request.getTopClass())) {
            List<ClassInfo> classInfos = classInfoService.getTopClassInfos(request.getType());
            for (ClassInfo classInfo : classInfos) {
                classes.add(classInfo.getTopClass());
            }
        } else {
            List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(request.getType(),
                    request.getTopClass());
            for (ClassInfo classInfo : classInfos) {
                classes.add(classInfo.getSecondClass());
            }
        }

        return ResponseModel.success(classes);
    }
}
