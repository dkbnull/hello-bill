package cn.wbnull.hellobill.dto.cls.request;

import cn.wbnull.hellobill.dto.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 分类信息分页查询接口请求参数
 *
 * @author null
 * @date 2025-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PageRequest {

    @NotEmpty(message = "type 不能为空")
    private String type;
}
