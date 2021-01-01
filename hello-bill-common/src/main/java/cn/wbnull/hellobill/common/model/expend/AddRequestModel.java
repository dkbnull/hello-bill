package cn.wbnull.hellobill.common.model.expend;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 新增账单接口请求参数
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class AddRequestModel extends RequestModel {

    private String expendTime;
    private String secondClass;
    private String detail;
    private String amount;
    private String remark;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
