package cn.wbnull.hellobill.common.model.income;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改收入信息接口请求参数
 *
 * @author dukunbiao(null)  2021-01-17
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class UpdateRequestModel {

    @NotEmpty(message = "uuid 不能为空")
    private String uuid;

    @NotEmpty(message = "incomeDate 不能为空")
    private String incomeDate;

    @NotEmpty(message = "secondClass 不能为空")
    private String secondClass;

    @NotEmpty(message = "detail 不能为空")
    private String detail;

    @NotEmpty(message = "amount 不能为空")
    private String amount;

    private String remark;
}
