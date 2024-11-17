package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.*;
import cn.wbnull.hellobill.model.expend.ExpendInfoModel;
import cn.wbnull.hellobill.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 支出信息接口
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("expend")
public class ExpendController extends BaseController {

    @Autowired
    private ExpendService expendService;

    /**
     * 支出信息明细查询接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "queryList")
    public ResponseModel<List<ExpendInfoModel>> queryList(@RequestBody @Validated RequestModel<QueryListRequestModel> request,
                                                          BindingResult result) throws Exception {
        super.validate(result);

        return expendService.queryList(request);
    }

    /**
     * 支出信息查询分类信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "classQuery")
    public ResponseModel<List<String>> classQuery(@RequestBody @Validated RequestModel<Object> request,
                                                  BindingResult result) throws Exception {
        super.validate(result);

        return expendService.classQuery(request);
    }

    /**
     * 新增支出信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "add")
    public ResponseModel<Object> add(@RequestBody @Validated RequestModel<AddRequestModel> request,
                                     BindingResult result) throws Exception {
        super.validate(result);

        return expendService.add(request);
    }

    /**
     * 支出信息查询接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "query")
    public ResponseModel<ExpendInfoModel> query(@RequestBody @Validated RequestModel<QueryRequestModel> request,
                                                BindingResult result) throws Exception {
        super.validate(result);

        return expendService.query(request);
    }

    /**
     * 修改支出信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "update")
    public ResponseModel<Object> update(@RequestBody @Validated RequestModel<UpdateRequestModel> request,
                                        BindingResult result) throws Exception {
        super.validate(result);

        return expendService.update(request);
    }

    /**
     * 删除支出信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "delete")
    public ResponseModel<Object> delete(@RequestBody @Validated RequestModel<DeleteRequestModel> request,
                                        BindingResult result) throws Exception {
        super.validate(result);

        return expendService.delete(request);
    }
}
