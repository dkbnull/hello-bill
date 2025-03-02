package cn.wbnull.hellobill.common.core.exception;

import cn.wbnull.hellobill.common.core.constant.ResponseCode;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3527079893854727117L;

    @Getter
    private String code;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String code, String message) {
        super(code + ": " + message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return (StringUtils.isEmpty(code) ? "" : (code + ": ")) + message;
    }
}
