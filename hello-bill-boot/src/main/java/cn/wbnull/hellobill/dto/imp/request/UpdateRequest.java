package cn.wbnull.hellobill.dto.imp.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改账单信息接口请求参数
 *
 * @author null
 * @date 2025-02-01
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class UpdateRequest {

    @NotEmpty(message = "id 不能为空")
    private String id;

    @NotNull(message = "billType 不能为空")
    private Integer billType;

    @NotEmpty(message = "secondClass 不能为空")
    private String secondClass;

    @NotEmpty(message = "detailConvert 不能为空")
    private String detailConvert;

    @NotEmpty(message = "amount 不能为空")
    private String amount;

    private String remark;
}
