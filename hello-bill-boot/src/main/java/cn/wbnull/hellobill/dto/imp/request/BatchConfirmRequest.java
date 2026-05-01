package cn.wbnull.hellobill.dto.imp.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量确认账单信息接口请求参数
 *
 * @author null
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class BatchConfirmRequest {

    @NotEmpty(message = "ids 不能为空")
    private List<String> ids;
}
