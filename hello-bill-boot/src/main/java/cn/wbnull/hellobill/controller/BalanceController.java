/*
 * Copyright (c) 2017-2025 null. All rights reserved.
 */

package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.balance.response.QueryResponse;
import cn.wbnull.hellobill.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资产信息接口
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@RestController
@Scope("prototype")
@RequestMapping("balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    /**
     * 资产负债查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "query")
    public ApiResponse<List<QueryResponse>> query(@RequestBody @Validated ApiRequest<Object> request) {
        return balanceService.query(request);
    }
}
