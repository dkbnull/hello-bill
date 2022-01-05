package cn.wbnull.hellobill.common.model.expend;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 删除支出、收入信息接口请求参数
 *
 * @author dukunbiao(null)  2021-01-03
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteRequestModel extends RequestModel {

    @NotEmpty(message = "uuid 不能为空")
    private String uuid;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
