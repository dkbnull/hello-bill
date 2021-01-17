package cn.wbnull.hellobill.common.model.expend;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 支出/收入信息查询接口请求参数
 *
 * @author dukunbiao(null)  2021-01-17
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class QueryRequestModel extends RequestModel {

    private String uuid;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
