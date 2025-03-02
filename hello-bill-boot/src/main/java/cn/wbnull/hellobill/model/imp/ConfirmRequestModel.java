package cn.wbnull.hellobill.model.imp;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 确认账单信息接口请求参数
 *
 * @author null  2025-02-01
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ConfirmRequestModel {

    @NotEmpty(message = "id 不能为空")
    private String id;
}
