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
     * 4. 优化反射性能
     * 5. 多线程验证集合
     * 6. 自定义异常
     *
     */
}
