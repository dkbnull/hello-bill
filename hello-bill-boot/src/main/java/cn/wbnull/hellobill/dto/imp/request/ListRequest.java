package cn.wbnull.hellobill.dto.imp.request;

import cn.wbnull.hellobill.dto.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导入账单明细查询接口请求参数
 *
 * @author null
 * @date 2025-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PageRequest {

}
