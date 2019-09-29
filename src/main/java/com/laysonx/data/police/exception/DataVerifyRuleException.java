package com.laysonx.data.police.exception;

/**
 * @description: 验证数据失败异常
 * @author: Laysonx
 * @date: 2019/9/26 17:11
 */
public class DataVerifyRuleException extends RuntimeException {

    public DataVerifyRuleException(String message) {
        super(message);
    }

    public DataVerifyRuleException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * TODO
     * 1. 启动规则校验
     * 2. @EnableDataPolice(import factory) 开启验证 ；效果：自动装配 拦截器 过滤器
     * 3. (FactoryBean return 双拦截器拦截器)
     * 4. 优化反射性能
     * 5. 多线程验证集合
     * 6. 自定义异常
     * 7. 启动时扫描所有 自定义handler 组装 config 用于验证时查找
     *
     *

     * 99. @EnableDataPolice (FactoryBean return 接口拦截器) 开启验证（可配置拦截规则） ；效果：注解开启，bean 开启
     */
}
