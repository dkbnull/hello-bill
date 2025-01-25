package cn.wbnull.hellobill.common.model;

import cn.wbnull.hellobill.common.constant.ResponseCodeEnum;
import cn.wbnull.hellobill.common.util.LoggerUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * 响应参数
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ResponseModel<T> {

    private String code;
    private String message;
    private T data;

    public static <T> ResponseModel<T> fail(String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = ResponseCodeEnum.FAIL.getCode();
        response.message = message;

        return response;
    }

    public static <T> ResponseModel<T> success(String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = ResponseCodeEnum.SUCCESS.getCode();
        response.message = message;

        return response;
    }

    public static <T> ResponseModel<T> success(T data) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = ResponseCodeEnum.SUCCESS.getCode();
        response.message = ResponseCodeEnum.SUCCESS.getMessage();
        response.data = data;

        return response;
    }

    public static <T> ResponseModel<T> response(String code, String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.code = code;
        response.message = message;

        return response;
    }

    @SuppressWarnings({"rawtypes"})
    public void log(RequestModel request) {
        HttpServletRequest servletRequest = request.initServletInfo();

        LoggerUtils.info("响应", servletRequest.getRequestURI(), this.toString());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
