package cn.wbnull.hellobill.model.common;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 支出、收入信息查询接口请求参数
 *
 * @author dukunbiao(null)  2021-01-17
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryRequestModel {

    @NotEmpty(message = "id 不能为空")
    private String id;
}
