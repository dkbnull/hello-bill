package cn.wbnull.hellobill.controller;

import cn.wbnull.hellobill.common.boot.GlobalException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * 控制器基类
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
public class BaseController {

    protected void validate(BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new GlobalException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
