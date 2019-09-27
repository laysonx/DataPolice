package com.laysonx.data.police.handler;

/**
 * @description: 数据验证接口
 * @author: Laysonx
 * @date: 2019/9/26 17:11
 */
public interface VerifyHandler {

    /**
     * @description 验证数据是否满足要求
     * @author: Laysonx
     * @date: 2019/9/26 18:15
     * @param t 等待验证的数据
     * @return: boolean
     */
    <T> boolean verify(T t);
}
