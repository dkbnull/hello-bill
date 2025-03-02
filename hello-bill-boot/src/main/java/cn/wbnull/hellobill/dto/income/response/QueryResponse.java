package cn.wbnull.hellobill.dto.income.response;

import lombok.Data;

/**
 * 收入信息查询接口响应参数
 *
 * @author null  2024-11-17
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryResponse {

    private String id;
    private String username;
    private String incomeDate;
    private String topClass;
    private String secondClass;
    private String detail;
    private String amount;
    private String remark;
}
