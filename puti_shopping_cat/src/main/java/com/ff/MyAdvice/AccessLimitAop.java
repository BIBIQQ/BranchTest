package com.ff.MyAdvice;

import com.ff.domain.Code;
import com.ff.exception.AccessLimitException;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author FF
 * @date 2021/11/21
 * @TIME:11:43
 */
@Component
@Aspect
public class AccessLimitAop {
    //限制执行数量  每秒钟  2 个
    private RateLimiter rateLimiter = RateLimiter.create(1.0);

    @Pointcut("@annotation(com.ff.anno.AccessLimit)")
    public void limit() {
    }

    @Around("limit()")
    public Object accessLimit(ProceedingJoinPoint pjp) {
        //获取令牌
        boolean flag = rateLimiter.tryAcquire();
        //判断是否获得到了 没有获得 抛出异常
        if (!flag) {
            //未获得令牌异常
            throw new AccessLimitException(Code.SYSTEM_TIMEOUT_ERR,"服务器繁忙稍后重试！");
        }

        Object proceed = null;
        try {
            proceed = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return proceed;
    }
}
