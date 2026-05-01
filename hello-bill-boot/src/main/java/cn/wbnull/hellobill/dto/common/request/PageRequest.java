package cn.wbnull.hellobill.dto.common.request;

import lombok.Data;

/**
 * 分页查询接口请求参数
 *
 * @author null
 * @date 2025-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class PageRequest {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
