package cn.wbnull.hellobill.dto.income.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改收入信息接口请求参数
 *
 * @author null
 * @date 2021-01-17
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class UpdateRequest {

    @NotEmpty(message = "id 不能为空")
    private String id;

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
