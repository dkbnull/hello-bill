package cn.wbnull.hellobill.common.model.expend;

import lombok.Data;

/**
 * 支出信息明细查询接口请求参数
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryListRequestModel {

    private String beginTime;
    private String endTime;
    private String topClass;
    private String secondClass;
    private String detail;
    private String order;

    public boolean orderByDesc() {
        return "2".equals(this.order);
    }
}
