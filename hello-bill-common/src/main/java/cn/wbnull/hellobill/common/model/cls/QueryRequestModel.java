package cn.wbnull.hellobill.common.model.cls;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 分类信息查询接口请求参数
 *
 * @author dukunbiao(null)  2022-01-05
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRequestModel extends RequestModel {

    @NotEmpty(message = "type 不能为空")
    private String type;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
