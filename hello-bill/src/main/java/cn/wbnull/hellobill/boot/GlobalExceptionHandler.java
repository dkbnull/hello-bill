package cn.wbnull.hellobill.boot;

import cn.wbnull.hellobill.common.boot.GlobalException;
import cn.wbnull.hellobill.common.util.LoggerUtils;
import cn.wbnull.hellobill.common.model.ResponseModel;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @SuppressWarnings({"rawtypes"})
    public ResponseModel exceptionHandler(HttpServletRequest servletRequest, Exception e) {
        ResponseModel response = ResponseModel.fail(e.getMessage());
        LoggerUtils.error("响应", servletRequest.getRequestURI(), response.toString());

        if (!notPrintStackTrace(e)) {
            LoggerUtils.error("响应异常", servletRequest.getRequestURI(), "", e);
        }

        return response;
    }

    private boolean notPrintStackTrace(Exception e) {
        return (e instanceof GlobalException
                || e instanceof HttpRequestMethodNotSupportedException)
                || (e instanceof MissingServletRequestParameterException);
    }
}
