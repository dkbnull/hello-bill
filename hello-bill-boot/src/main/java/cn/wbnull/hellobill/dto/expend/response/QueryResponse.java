package cn.wbnull.hellobill.dto.expend.response;

import lombok.Data;

/**
 * 支出信息查询接口响应参数
 *
 * @author null
 * @date 2024-11-17
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class QueryResponse {

    private String id;
    private String username;
    private String expendTime;
    private String topClass;
    private String secondClass;
    private String detail;
    private String amount;
    private String remark;
}
