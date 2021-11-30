package com.ff.MyAdvice;

import com.ff.exception.AccessLimitException;
import com.ff.exception.BusinessException;
import com.ff.exception.SystemException;
import com.ff.domain.Code;
import com.ff.domain.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author FF
 * @date 2021/11/12
 * @TIME:12:01
 */
//声明成异常处理器类
@RestControllerAdvice
public class ProjectExceptionAdvice {
    //声明处理哪一类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex) {
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(Code.SYSTEM_UNKNOW_ERR, null, "系统繁忙，请稍后再试3！");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result doRuntimeException(RuntimeException ex) {
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员
        return new Result(Code.SYSTEM_UNKNOW_ERR, null, "系统繁忙，请稍后再试2！");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result doNullPointerException(NullPointerException ex) {
        return new Result(Code.SYSTEM_UNKNOW_ERR, null, "系统繁忙，请稍后再试1！");
    }
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public Result doSystemException(SystemException ex) {
        return new Result(ex.getCode(), null, ex.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result doBusinessException(BusinessException ex) {
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    /**
     * //未获得令牌异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessLimitException.class)
    @ResponseBody
    public Result doAccessLimitException(AccessLimitException ex) {
        return new Result(ex.getCode(), null, ex.getMessage());
    }
}
