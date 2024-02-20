package cn.wbnull.hellobill.common.model.income;

import lombok.Data;

/**
 * 收入信息明细查询接口请求参数
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryListRequestModel {

    private String beginDate;
    private String endDate;
    private String secondClass;
    private String detail;
    private String order;

    public boolean orderByDesc() {
        return "2".equals(this.order);
    }
}
