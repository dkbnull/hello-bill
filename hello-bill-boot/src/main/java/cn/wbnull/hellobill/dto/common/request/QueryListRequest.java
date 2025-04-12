package cn.wbnull.hellobill.dto.common.request;

import lombok.Data;

/**
 * 收入/支出信息明细查询接口请求参数
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class QueryListRequest {

    private String beginDate;
    private String endDate;
    private String topClass;
    private String secondClass;
    private String detail;
    private String order;
}
