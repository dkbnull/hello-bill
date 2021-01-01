package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryRequestModel;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.service.IncomeService;
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
 * 收入信息接口
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("income")
public class IncomeController extends BaseController {

    @Autowired
    private IncomeService incomeService;

    /**
     * 收入信息查询接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "query")
    public ResponseModel<List<IncomeInfo>> query(@RequestBody @Validated QueryRequestModel request,
                                                 BindingResult result) throws Exception {
        super.validate(result);

        return incomeService.query(request);
    }

    /**
     * 收入信息分类查询分类信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "classQuery")
    public ResponseModel<List<String>> classQuery(@RequestBody @Validated RequestModel request,
                                                  BindingResult result) throws Exception {
        super.validate(result);

        return incomeService.classQuery(request);
    }

    /**
     * 新增收入明细接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "add")
    public ResponseModel<Object> add(@RequestBody @Validated AddRequestModel request,
                                     BindingResult result) throws Exception {
        super.validate(result);

        return incomeService.add(request);
    }
}
