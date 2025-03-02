package cn.wbnull.hellobill.common.core.exception;

import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.LoggerUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> exceptionHandler(HttpServletRequest servletRequest, Exception e) {
        ApiResponse<Object> response = ApiResponse.fail(e.getMessage());
        LoggerUtils.error("响应", servletRequest.getRequestURI(), response.toString());

        if (!notPrintStackTrace(e)) {
            LoggerUtils.error("响应异常", servletRequest.getRequestURI(), "", e);
        }

        return response;
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Object> globalExceptionHandler(HttpServletRequest servletRequest, BusinessException e) {
        ApiResponse<Object> response = ApiResponse.response(e.getCode(), e.getMessage());
        LoggerUtils.error("响应", servletRequest.getRequestURI(), response.toString());
        return response;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> methodArgumentNotValidExceptionHandler(HttpServletRequest servletRequest,
                                                                      MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return ApiResponse.fail(message);
    }

    private boolean notPrintStackTrace(Exception e) {
        return (e instanceof BusinessException
                || e instanceof HttpRequestMethodNotSupportedException)
                || (e instanceof MissingServletRequestParameterException);
    }
}
