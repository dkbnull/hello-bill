package cn.wbnull.hellobill.common.model.income;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收入信息明细查询接口请求参数
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryListRequestModel extends RequestModel {

    private String beginDate;
    private String endDate;
    private String secondClass;
    private String detail;
    private String order;

    public boolean orderByDesc() {
        return "2".equals(this.order);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
