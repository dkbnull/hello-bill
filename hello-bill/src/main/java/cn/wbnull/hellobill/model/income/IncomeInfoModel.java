package cn.wbnull.hellobill.model.income;

import lombok.Data;

/**
 * 收入信息Model
 *
 * @author dukunbiao(null)  2024-11-17
 */
@Data
public class IncomeInfoModel {

    private String id;
    private String username;
    private String incomeDate;
    private String topClass;
    private String secondClass;
    private String detail;
    private String amount;
    private String remark;
}
