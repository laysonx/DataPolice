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
     * 4. 优化反射性能
     * 5. 多线程验证集合
     * 6. 全局异常信息 + 方法级异常信息
     * 7. 支持 JavaConfig 方式开启 EnableDataPolice
     */
}
