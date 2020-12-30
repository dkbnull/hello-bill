package cn.wbnull.hellobill.model.bill;

import cn.wbnull.hellobill.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 账单信息接口请求参数
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class InfoRequestModel extends RequestModel {

    private String beginTime;
    private String endTime;
    private String topClass;
    private String secondClass;
    private String detail;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
