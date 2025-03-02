package cn.wbnull.hellobill.common.core.aop;

import cn.wbnull.hellobill.common.core.model.RequestModel;
import cn.wbnull.hellobill.common.core.model.ResponseModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author null  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * cn.wbnull.hellobill.controller.*.*(..))" +
            "&& !execution(public * cn.wbnull.hellobill.controller.ImportController.billFile(..))")
    private void loggingAspect() {

    }

    @SuppressWarnings({"rawtypes"})
    @Before("loggingAspect()")
    public void doBefore(JoinPoint joinPoint) {
        RequestModel request = (RequestModel) joinPoint.getArgs()[0];

        request.log();
    }

    @SuppressWarnings({"rawtypes"})
    @AfterReturning(value = "loggingAspect()", returning = "response")
    public ResponseModel doAfter(JoinPoint joinPoint, ResponseModel response) {
        response.log((RequestModel) joinPoint.getArgs()[0]);

        return response;
    }
}
