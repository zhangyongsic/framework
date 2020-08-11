package com.zhangyongsic.framework.web.biz;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/11
 */
@Aspect
@Component
@Order(1001)
public class BizRootAop {

    @Pointcut("@annotation(com.zhangyongsic.framework.web.lock.Lock)")
    public void rootPoint() {
    }


    @Around("rootPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof AbstractBiz) {
                    AbstractBiz biz = (AbstractBiz) arg;
                    biz.setRoot();
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
