package cn.wbnull.hellobill.dto.expend.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 新增支出信息接口请求参数
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class AddRequest {

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
