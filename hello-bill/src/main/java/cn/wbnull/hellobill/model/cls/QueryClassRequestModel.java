package cn.wbnull.hellobill.model.cls;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 报表分类查询接口请求参数
 *
 * @author null  2024-02-11
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryClassRequestModel {

    @NotEmpty(message = "type 不能为空")
    private String type;

    private String topClass;
}
