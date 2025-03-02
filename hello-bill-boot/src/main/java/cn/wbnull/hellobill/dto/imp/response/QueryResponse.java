package cn.wbnull.hellobill.dto.imp.response;

import lombok.Data;

/**
 * 账单明细列表查询接口响应参数
 *
 * @author null  2025-01-27
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryResponse {

    private String id;
    private String username;
    private String billType;
    private String billTypeValue;
    private String billTime;
    private String topClass;
    private String secondClass;
    private String detail;
    private String detailConvert;
    private String amount;
    private String payMode;
    private String remark;
    private String createTime;
    private String updateTime;
}
