package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataVerify;
import com.laysonx.data.police.util.DataVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @description: 配置启动入口扫描器
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
@Aspect
@Slf4j
public class DataVerifyEntranceConfigurer {

    @Pointcut("@annotation(com.laysonx.data.police.annotation.EnableDataVerify)")
    public void findEntrance(){}


    @Around("findEntrance()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // 检查是否需要开启校验
        EnableDataVerify enableDataVerify = method.getAnnotation(EnableDataVerify.class);
        if (enableDataVerify != null) {
            log.debug("DataPolice 监控到接口 {} 需要验证数据",method.getName());
            DataVerifyUtil.openVerify(enableDataVerify);
        } else {
            log.debug("DataPolice 监控到接口 {} 不需要验证数据",method.getName());
        }
        Object proceed = pjp.proceed();
        DataVerifyUtil.closeVerify();
        return proceed;
    }

}
