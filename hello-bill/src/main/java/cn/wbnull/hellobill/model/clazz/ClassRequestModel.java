package cn.wbnull.hellobill.model.clazz;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 报表分类查询接口请求参数
 *
 * @author dukunbiao(null)  2024-02-11
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClassRequestModel extends RequestModel {

    @NotEmpty(message = "type 不能为空")
    private String type;

    private String topClass;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
