package cn.wbnull.hellobill.model.cls;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 分类信息更新接口请求参数
 *
 * @author null  2022-01-04
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class UpdateRequestModel {

    @NotEmpty(message = "uuid 不能为空")
    private String uuid;

    @NotEmpty(message = "key 不能为空")
    private String key;

    @NotNull(message = "value 不能为空")
    private String value;
}
