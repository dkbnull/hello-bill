package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.income.AddRequestModel;
import cn.wbnull.hellobill.model.income.IncomeInfoModel;
import cn.wbnull.hellobill.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收入信息接口
 *
 * @author null  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    /**
     * 收入信息明细查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryList")
    public ResponseModel<List<IncomeInfoModel>> queryList(@RequestBody @Validated RequestModel<QueryListRequestModel> request) {
        return incomeService.queryList(request);
    }

    /**
     * 新增收入信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "add")
    public ResponseModel<Object> add(@RequestBody @Validated RequestModel<AddRequestModel> request) {
        return incomeService.add(request);
    }

    /**
     * 收入信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ResponseModel<IncomeInfoModel> query(@RequestBody @Validated RequestModel<QueryRequestModel> request) {
        return incomeService.query(request);
    }

    /**
     * 修改收入信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ResponseModel<Object> update(@RequestBody @Validated RequestModel<UpdateRequestModel> request) {
        return incomeService.update(request);
    }

    /**
     * 删除收入信息接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "delete")
    public ResponseModel<Object> delete(@RequestBody @Validated RequestModel<DeleteRequestModel> request) {
        return incomeService.delete(request);
    }
}
