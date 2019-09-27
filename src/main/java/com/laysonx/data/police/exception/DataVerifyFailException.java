package com.laysonx.data.police.exception;

/**
 * @description: 验证数据失败异常
 * @author: Laysonx
 * @date: 2019/9/26 17:11
 */
public class DataVerifyFailException extends RuntimeException {

    public DataVerifyFailException(String message) {
        super(message);
    }

    public DataVerifyFailException(String message, Throwable cause) {
        super(message, cause);
    }

}
