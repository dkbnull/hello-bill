package cn.wbnull.hellobill.common.core.model.common;

import lombok.Data;

/**
 * 支出信息明细查询接口请求参数
 *
 * @author null  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryListRequestModel {

    private String beginDate;
    private String endDate;
    private String topClass;
    private String secondClass;
    private String detail;
    private String order;

    public boolean orderByAsc() {
        return !"2".equals(this.order);
    }
}
