package cn.wbnull.hellobill.common.model.cls;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 分类信息更新接口请求参数
 *
 * @author dukunbiao(null)  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateRequestModel extends RequestModel {

    @NotEmpty(message = "uuid 不能为空")
    private String uuid;

    @NotEmpty(message = "key 不能为空")
    private String key;

    @NotNull(message = "value 不能为空")
    private String value;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
