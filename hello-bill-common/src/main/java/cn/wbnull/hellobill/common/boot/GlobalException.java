package cn.wbnull.hellobill.common.boot;

import cn.wbnull.hellobill.common.constant.ResponseCodeEnum;
import cn.wbnull.hellobill.common.util.StringUtils;
import lombok.Getter;

/**
 * 全局异常
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 3527079893854727117L;

    @Getter
    private String code;
    private String message;

    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public GlobalException(String code, String message) {
        super(code + ": " + message);
        this.code = code;
        this.message = message;
    }

    public GlobalException(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public GlobalException(Throwable throwable) {
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
