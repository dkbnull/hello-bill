package cn.wbnull.hellobill.dto.common.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 删除支出/收入信息接口请求参数
 *
 * @author null  2021-01-03
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class DeleteRequest {

    @NotEmpty(message = "id 不能为空")
    private String id;
}
