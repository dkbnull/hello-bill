package cn.wbnull.hellobill.common.boot;

import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.util.LoggerUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseModel<Object> exceptionHandler(HttpServletRequest servletRequest, Exception e) {
        ResponseModel<Object> response = ResponseModel.fail(e.getMessage());
        LoggerUtils.error("响应", servletRequest.getRequestURI(), response.toString());

        if (!notPrintStackTrace(e)) {
            LoggerUtils.error("响应异常", servletRequest.getRequestURI(), "", e);
        }

        return response;
    }

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ResponseModel<Object> globalExceptionHandler(HttpServletRequest servletRequest, GlobalException e) {
        ResponseModel<Object> response = ResponseModel.response(e.getCode(), e.getMessage());
        LoggerUtils.error("响应", servletRequest.getRequestURI(), response.toString());
        return response;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel<Object> methodArgumentNotValidExceptionHandler(HttpServletRequest servletRequest,
                                                                        MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return ResponseModel.fail(message);
    }

    private boolean notPrintStackTrace(Exception e) {
        return (e instanceof GlobalException
                || e instanceof HttpRequestMethodNotSupportedException)
                || (e instanceof MissingServletRequestParameterException);
    }
}
