package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.cls.request.QueryClassRequest;
import cn.wbnull.hellobill.dto.cls.request.QueryRequest;
import cn.wbnull.hellobill.dto.cls.request.UpdateRequest;
import cn.wbnull.hellobill.dto.cls.response.QueryResponse;
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
 * @author null
 * @date 2022-01-04
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
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
     */
    @PostMapping(value = "query")
    public ApiResponse<List<QueryResponse>> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return classService.query(request);
    }

    /**
     * 分类信息更新接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return classService.update(request);
    }

    /**
     * 二级分类信息查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "secondClassQuery")
    public ApiResponse<List<String>> secondClassQuery(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return classService.secondClassQuery(request);
    }

    /**
     * 报表分类查询接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "queryClass")
    public ApiResponse<List<String>> queryClass(@RequestBody @Validated ApiRequest<QueryClassRequest> request) {
        return classService.queryClass(request);
    }
}
