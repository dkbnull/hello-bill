package cn.wbnull.hellobill.dto.income.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 新增收入信息接口请求参数
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class AddRequest {

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
