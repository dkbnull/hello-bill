package cn.wbnull.hellobill.model.common;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 删除支出、收入信息接口请求参数
 *
 * @author dukunbiao(null)  2021-01-03
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class DeleteRequestModel {

    @NotEmpty(message = "id 不能为空")
    private String id;
}
