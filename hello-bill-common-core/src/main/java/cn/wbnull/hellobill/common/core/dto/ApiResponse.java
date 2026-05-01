package cn.wbnull.hellobill.common.core.dto;

import cn.wbnull.hellobill.common.core.constant.ResponseCodeEnum;
import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * 响应参数
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class ApiResponse<T> {

    private String code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = ResponseCodeEnum.FAIL.getCode();
        response.msg = message;

        return response;
    }

    public static <T> ApiResponse<T> success(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = ResponseCodeEnum.SUCCESS.getCode();
        response.msg = message;

        return response;
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = ResponseCodeEnum.SUCCESS.getCode();
        response.msg = ResponseCodeEnum.SUCCESS.getMessage();
        response.data = data;

        return response;
    }

    public static <T> ApiResponse<T> response(String code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = code;
        response.msg = message;

        return response;
    }

    @SuppressWarnings({"rawtypes"})
    public void log(ApiRequest request) {
        HttpServletRequest servletRequest = request.initServletInfo();

        LoggerUtils.info("响应", servletRequest.getRequestURI(), this.toString());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
