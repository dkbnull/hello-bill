package cn.wbnull.hellobill.model.imp;

import lombok.Data;

/**
 * 账单明细列表查询接口响应参数
 *
 * @author null  2025-01-27
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ImportBillInfoModel {

    private String id;
    private String username;
    private String billType;
    private String billTime;
    private String topClass;
    private String secondClass;
    private String detail;
    private String amount;
    private String remark;
    private String createTime;
    private String updateTime;
}
