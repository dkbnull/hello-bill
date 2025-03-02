package cn.wbnull.hellobill.dto.cls.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 报表分类查询接口请求参数
 *
 * @author null
 * @date 2024-02-11
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class QueryClassRequest {

    @NotEmpty(message = "type 不能为空")
    private String type;

    private String topClass;
}
