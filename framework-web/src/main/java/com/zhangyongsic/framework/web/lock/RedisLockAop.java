package com.zhangyongsic.framework.web.lock;


import com.zhangyongsic.framework.redis.lock.RedisLockSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @program: framework
 * @description: 多切面 order越小越是最先执行
 * @author: zhang yong
 * @create: 2020/08/11
 */
@Aspect
@Component
@Order(1002)
public class RedisLockAop {

    @Autowired
    @Qualifier("redisLockSupport")
    private RedisLockSupport redisLockSupport;

    @Pointcut("@annotation(com.zhangyongsic.framework.web.lock.Lock)")
    public void lockPoint() {
    }

    /**
     * 加锁
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("lockPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof Lockable) {
                    Lockable lockable = (Lockable) arg;
                    if (lockable.listLocks() != null) {
                        for (String key : lockable.listLocks()) {
                            redisLockSupport.lock(key);
                        }
                    }
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * 解锁
     *
     * @param joinPoint
     */
    @After("lockPoint()")
    public void doAfter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof Lockable) {
                    Lockable lockable = (Lockable) arg;
                    if (lockable.listLocks() != null) {
                        for (String key : lockable.listLocks()) {
                            redisLockSupport.unlock(key);
                        }
                    }
                }
            }
        }
    }
}
