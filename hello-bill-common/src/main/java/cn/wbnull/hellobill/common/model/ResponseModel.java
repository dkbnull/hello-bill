package cn.wbnull.hellobill.common.model;

import cn.wbnull.hellobill.common.util.LoggerUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * 响应参数
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ResponseModel<T> {

    private static final String FAIL = "1000";
    private static final String SUCCESS = "2000";

    private String code;
    private String message;
    private T data;

    public static <T> ResponseModel<T> fail(String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = FAIL;
        response.message = message;

        return response;
    }

    public static <T> ResponseModel<T> success(String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = SUCCESS;
        response.message = message;

        return response;
    }

    public static <T> ResponseModel<T> success(T data) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = SUCCESS;
        response.message = "SUCCESS";
        response.data = data;

        return response;
    }

    public void log(RequestModel request) {
        HttpServletRequest servletRequest = request.servletInfo();

        LoggerUtils.info("响应", servletRequest.getRequestURI(), this.toString());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
