package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.model.cls.ClassRequestModel;
import cn.wbnull.hellobill.model.cls.QueryRequestModel;
import cn.wbnull.hellobill.model.cls.UpdateRequestModel;
import cn.wbnull.hellobill.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类信息接口
 *
 * @author dukunbiao(null)  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("class")
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 分类信息查询接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "query")
    public ResponseModel<List<ClassInfo>> query(@RequestBody @Validated RequestModel<QueryRequestModel> request) throws Exception {
        return classService.query(request);
    }

    /**
     * 分类信息更新接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "update")
    public ResponseModel<Object> update(@RequestBody @Validated RequestModel<UpdateRequestModel> request) throws Exception {
        return classService.update(request);
    }

    /**
     * 报表分类查询接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "queryClass")
    public ResponseModel<List<String>> queryClass(@RequestBody @Validated RequestModel<ClassRequestModel> request) throws Exception {
        return classService.queryClass(request);
    }
}
