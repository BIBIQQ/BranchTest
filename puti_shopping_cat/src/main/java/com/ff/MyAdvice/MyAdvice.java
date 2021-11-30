package com.ff.MyAdvice;

import com.ff.dao.MethodLogInfoDao;
import com.ff.dao.UserDao;
import com.ff.domain.Code;
import com.ff.domain.MethodLogInfo;
import com.ff.domain.Result;
import com.ff.domain.User;
import com.ff.exception.SystemException;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author FF
 * @date 2021/11/20
 * @TIME:12:28
 */
@Component
@Aspect
public class MyAdvice {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MethodLogInfoDao methodLogInfoDao;

    @Pointcut("execution(* com.ff.service.IShoppingCartService.*(..))")
    public void methodLog(){}

    @Autowired
    private  HttpServletRequest request;


    /**
     * 校验用户Id  是否合规
     * 并且记录日志
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("methodLog()")
    public Object checkByUserId(ProceedingJoinPoint pjp) {

        MethodLogInfo methodLogInfo = new MethodLogInfo();
        //获取当前的时间
        long start = System.currentTimeMillis();
        methodLogInfo.setMethodEnd(LocalDateTime.now());
        //获得签名对象
        Signature signature = pjp.getSignature();
        //设置执行方法方法名
        methodLogInfo.setMethod(signature.getName());

        //获取参数
        Object[] args = pjp.getArgs();
        Long userId = null;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if(arg.getClass().equals(Long.class)){
                userId = (Long) arg;
            }
        }


        //校验参数是否准确
        if(userId == null){
            return Result.error("参数错误，请重新尝试");
        }
        //校验用户是否有用户信息
        User userById = userDao.selectById(userId);
        if(userById == null){
            return  Result.error("该用户没有注册,请注册后重新登录");
        }
        Object proceed = null;

        try {
            //执行原始方法
            proceed = pjp.proceed(args);
        } catch (Throwable throwable) {
            methodLogInfo.setMethod(signature.getName()+"   "+"error");
            throw  new SystemException(Code.SYSTEM_UNKNOW_ERR,"出现异常");
        } finally {

            //获取结束时间
            long end = System.currentTimeMillis();
            //获取时间差
            long methodTime =  end -start;
            //设置参数
            methodLogInfo.setMethodTime(methodTime);
            methodLogInfo.setMethodStart(LocalDateTime.now());
            methodLogInfo.setUserId(userId);
            //获取请求IP
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
            //记录访问ip
            methodLogInfo.setIp( request.getRemoteAddr());

            //存储日志
            methodLogInfoDao.insert(methodLogInfo);
        }

        return proceed;
    }


}
