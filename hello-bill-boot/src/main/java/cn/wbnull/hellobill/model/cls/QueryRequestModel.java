package cn.wbnull.hellobill.model.cls;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 分类信息查询接口请求参数
 *
 * @author null  2022-01-05
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryRequestModel {

    @NotEmpty(message = "type 不能为空")
    private String type;
}
