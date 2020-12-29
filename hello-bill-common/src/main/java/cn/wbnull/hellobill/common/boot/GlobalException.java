package cn.wbnull.hellobill.common.boot;

import cn.wbnull.hellobill.common.util.StringUtils;

/**
 * 全局异常
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public class GlobalException extends Exception {

    private static final long serialVersionUID = 3527079893854727117L;

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

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public GlobalException(Throwable throwable) {
        super(throwable);
    }

    public String getCode() {
        return code;
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
