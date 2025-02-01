package cn.wbnull.hellobill.model.imp;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改账单信息接口请求参数
 *
 * @author null  2025-02-01
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class UpdateRequestModel {

    @NotEmpty(message = "id 不能为空")
    private String id;

    @NotEmpty(message = "billType 不能为空")
    private String billType;

    @NotEmpty(message = "secondClass 不能为空")
    private String secondClass;

    @NotEmpty(message = "detail 不能为空")
    private String detail;

    @NotEmpty(message = "amount 不能为空")
    private String amount;

    private String remark;
}
