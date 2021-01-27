package cn.wbnull.hellobill.common.model.expend;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

/**
 * 支出、收入信息报表接口响应参数
 *
 * @author dukunbiao(null)  2021-01-26
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ReportResponseModel<T> {

    private List<T> reportClass;
    private JSONArray reportDate;
    private JSONArray date;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
