package cn.wbnull.hellobill.dto.imp.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 确认账单信息接口请求参数
 *
 * @author null
 * @date 2025-02-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class ConfirmRequest {

    @NotEmpty(message = "id 不能为空")
    private String id;
}
