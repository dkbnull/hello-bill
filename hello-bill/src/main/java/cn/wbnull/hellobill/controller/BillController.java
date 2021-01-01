package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.db.entity.BillInfo;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.bill.AddRequestModel;
import cn.wbnull.hellobill.common.model.bill.InfoRequestModel;
import cn.wbnull.hellobill.service.BillService;
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
 * 账单接口
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@RestController
@Scope("prototype")
@RequestMapping("bill")
public class BillController extends BaseController {

    @Autowired
    private BillService billService;

    /**
     * 账单信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "info")
    public ResponseModel<List<BillInfo>> info(@RequestBody @Validated InfoRequestModel request, BindingResult result) throws Exception {
        super.validate(result);

        return billService.info(request);
    }

    /**
     * 二级分类信息接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "classInfo")
    public ResponseModel<List<String>> classInfo(@RequestBody @Validated RequestModel request, BindingResult result) throws Exception {
        super.validate(result);

        return billService.classInfo(request);
    }

    /**
     * 新增账单接口
     *
     * @param request
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping(value = "add")
    public ResponseModel<Object> add(@RequestBody @Validated AddRequestModel request, BindingResult result) throws Exception {
        super.validate(result);

        return billService.add(request);
    }
}
