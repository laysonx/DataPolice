package com.laysonx.data.police.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @description: 配置后启动数据层扫描器
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
@Getter
@Setter
@ToString
public class DataVerifyInfoConfigurer implements MethodInterceptor {

    private DataVerifyInfoChecker infoChecker ;

    public DataVerifyInfoConfigurer(DataVerifyInfoChecker infoChecker) {
        this.infoChecker = infoChecker;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return infoChecker.around(invocation);
    }
}
