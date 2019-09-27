package com.laysonx.data.police.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 配置后启动入口扫描器
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
@Aspect
public class DataVerifyInfoConfigurer{

    private DataVerifyInfoScanner infoScanner ;

    public DataVerifyInfoConfigurer(DataVerifyInfoScanner infoScanner) {
        this.infoScanner = infoScanner;
    }

    @Pointcut("execution(* com..*.service..*.*(..))")
    public void findData(){}


    @Around("findData()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return infoScanner.around(pjp);
    }

}
