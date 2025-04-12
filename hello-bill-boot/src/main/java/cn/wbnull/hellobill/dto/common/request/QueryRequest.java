package cn.wbnull.hellobill.dto.common.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 收入/支出信息查询接口请求参数
 *
 * @author null
 * @date 2021-01-17
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class QueryRequest {

    @NotEmpty(message = "id 不能为空")
    private String id;
}
