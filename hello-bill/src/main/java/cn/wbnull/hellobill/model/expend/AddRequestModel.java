package cn.wbnull.hellobill.model.expend;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 新增支出信息接口请求参数
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class AddRequestModel {

    @NotEmpty(message = "expendTime 不能为空")
    private String expendTime;

    @NotEmpty(message = "secondClass 不能为空")
    private String secondClass;

    @NotEmpty(message = "detail 不能为空")
    private String detail;

    @NotEmpty(message = "amount 不能为空")
    private String amount;

    private String remark;
}
