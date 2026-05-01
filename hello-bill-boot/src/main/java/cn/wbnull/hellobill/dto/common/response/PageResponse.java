package cn.wbnull.hellobill.dto.common.response;

import lombok.Data;

import java.util.List;

/**
 * 分页查询接口响应参数
 *
 * @author null
 * @date 2025-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class PageResponse<T> {

    private List<T> records;
    private Long total;
    private Long size;
    private Long current;

    public static <T> PageResponse<T> of(List<T> records, Long total, Long size, Long current) {
        PageResponse<T> response = new PageResponse<>();
        response.setRecords(records);
        response.setTotal(total);
        response.setSize(size);
        response.setCurrent(current);
        return response;
    }
}
