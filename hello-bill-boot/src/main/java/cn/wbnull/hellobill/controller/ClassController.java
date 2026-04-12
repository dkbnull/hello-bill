package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.dto.cls.request.QueryClassRequest;
import cn.wbnull.hellobill.dto.cls.request.QueryRequest;
import cn.wbnull.hellobill.dto.cls.request.UpdateRequest;
import cn.wbnull.hellobill.dto.cls.response.QueryResponse;
import cn.wbnull.hellobill.service.ClassService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping(value = "query")
    public ApiResponse<List<QueryResponse>> query(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return classService.query(request);
    }

    @PostMapping(value = "update")
    public ApiResponse<Object> update(@RequestBody @Validated ApiRequest<UpdateRequest> request) {
        return classService.update(request);
    }

    @PostMapping(value = "secondClassQuery")
    public ApiResponse<List<String>> secondClassQuery(@RequestBody @Validated ApiRequest<QueryRequest> request) {
        return classService.secondClassQuery(request);
    }

    @PostMapping(value = "queryClass")
    public ApiResponse<List<String>> queryClass(@RequestBody @Validated ApiRequest<QueryClassRequest> request) {
        return classService.queryClass(request);
    }
}
