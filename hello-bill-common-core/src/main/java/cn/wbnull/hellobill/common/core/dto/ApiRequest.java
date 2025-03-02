package cn.wbnull.hellobill.common.core.dto;

import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import cn.wbnull.hellobill.common.core.util.ServletUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 请求参数
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ApiRequest<T> {

    @NotEmpty(message = "username 不能为空")
    private String username;

    @NotNull(message = "timestamp 不能为空")
    private Long timestamp;

    @NotEmpty(message = "nonce 不能为空")
    private String nonce;

    @NotEmpty(message = "sign 不能为空")
    private String sign;

    @Valid
    @NotNull(message = "data 不能为空")
    private T data;

    @JsonIgnore
    @JSONField(serialize = false)
    private HttpServletRequest servletRequest;

    public void log() {
        LoggerUtils.info("请求", initServletInfo().getRequestURI(), this.toString());
    }

    public HttpServletRequest initServletInfo() {
        if (this.servletRequest == null) {
            this.servletRequest = ServletUtils.getHttpServletRequest();
        }

        return this.servletRequest;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
