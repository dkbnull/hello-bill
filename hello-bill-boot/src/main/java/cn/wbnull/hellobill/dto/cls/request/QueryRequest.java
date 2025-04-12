package cn.wbnull.hellobill.dto.cls.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 分类信息查询接口请求参数
 *
 * @author null
 * @date 2022-01-05
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class QueryRequest {

    @NotEmpty(message = "type 不能为空")
    private String type;
}
