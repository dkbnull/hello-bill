package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.expend.*;
import cn.wbnull.hellobill.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 支出信息接口
 *
 * @author null  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("expend")
public class ExpendController {

    @Autowired
    private ExpendService expendService;

    /**
     * 支出信息明细查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryList")
    public ResponseModel<List<ExpendInfoModel>> queryList(@RequestBody @Validated RequestModel<QueryListRequestModel> request) {
        return expendService.queryList(request);
    }

    /**
     * 新增支出信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "add")
    public ResponseModel<Object> add(@RequestBody @Validated RequestModel<AddRequestModel> request) {
        return expendService.add(request);
    }

    /**
     * 支出信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ResponseModel<ExpendInfoModel> query(@RequestBody @Validated RequestModel<QueryRequestModel> request) {
        return expendService.query(request);
    }

    /**
     * 修改支出信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ResponseModel<Object> update(@RequestBody @Validated RequestModel<UpdateRequestModel> request) {
        return expendService.update(request);
    }

    /**
     * 删除支出信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "delete")
    public ResponseModel<Object> delete(@RequestBody @Validated RequestModel<DeleteRequestModel> request) {
        return expendService.delete(request);
    }
}
