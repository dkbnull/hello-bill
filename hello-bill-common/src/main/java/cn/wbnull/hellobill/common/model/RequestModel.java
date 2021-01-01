package cn.wbnull.hellobill.common.model;

import cn.wbnull.hellobill.common.util.LoggerUtils;
import cn.wbnull.hellobill.common.util.ServletUtils;
import com.alibaba.fastjson.JSON;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;

/**
 * 请求参数
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class RequestModel {

    @NotEmpty(message = "username 不能为空")
    protected String username;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HttpServletRequest servletRequest;

    public void log() {
        LoggerUtils.info("请求", servletInfo().getRequestURI(), this.toString());
    }

    public HttpServletRequest servletInfo() {
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
