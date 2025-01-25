package cn.wbnull.hellobill.model.expend;

import lombok.Data;

/**
 * 支出信息Model
 *
 * @author null  2024-11-17
 */
@Data
public class ExpendInfoModel {

    private String id;
    private String username;
    private String expendTime;
    private String topClass;
    private String secondClass;
    private String detail;
    private String amount;
    private String remark;
}
