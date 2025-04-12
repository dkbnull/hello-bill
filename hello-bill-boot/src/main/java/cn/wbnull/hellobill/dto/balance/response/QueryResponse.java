package cn.wbnull.hellobill.dto.balance.response;

import lombok.Data;

/**
 * QueryResponse
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class QueryResponse {

    private String id;
    private String balanceDate;
    private String incomeAmount;
    private String expendAmount;
    private String balanceAmount;
    private String remark;
}
