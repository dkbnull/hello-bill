package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.model.cls.QueryRequestModel;
import cn.wbnull.hellobill.model.cls.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.model.cls.ClassRequestModel;
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

    public ResponseModel<List<ClassInfo>> query(RequestModel<QueryRequestModel> request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getClassInfos(request.getData().getType());
        for (ClassInfo classInfo : classInfos) {
            classInfo.analyseInfo();
        }

        return ResponseModel.success(classInfos);
    }

    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) throws Exception {
        UpdateRequestModel data = request.getData();

        classInfoService.updateClassInfo(data.getUuid(), data.getKey(), data.getValue());
        return ResponseModel.success("分类信息更新成功");
    }

    public ResponseModel<List<String>> queryClass(RequestModel<ClassRequestModel> request) throws Exception {
        ClassRequestModel data = request.getData();

        List<String> classes = new ArrayList<>();
        if (StringUtils.isEmpty(data.getTopClass())) {
            List<ClassInfo> classInfos = classInfoService.getTopClassInfos(data.getType());
            for (ClassInfo classInfo : classInfos) {
                classes.add(classInfo.getTopClass());
            }
        } else {
            List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(data.getType(),
                    data.getTopClass());
            for (ClassInfo classInfo : classInfos) {
                classes.add(classInfo.getSecondClass());
            }
        }

        return ResponseModel.success(classes);
    }
}
