package cn.wbnull.hellobill.boot;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 全局AOP拦截器
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Aspect
@Component
public class GlobalAop {

    @Pointcut("execution(public * cn.wbnull.hellobill.controller.*.*(..))")
    private void globalAop() {

    }

    @SuppressWarnings({"rawtypes"})
    @Before("globalAop()")
    public void doBefore(JoinPoint joinPoint) {
        RequestModel request = (RequestModel) joinPoint.getArgs()[0];

        request.log();
    }

    @SuppressWarnings({"rawtypes"})
    @AfterReturning(value = "globalAop()", returning = "response")
    public ResponseModel doAfter(JoinPoint joinPoint, ResponseModel response) {
        response.log((RequestModel) joinPoint.getArgs()[0]);

        return response;
    }
}
