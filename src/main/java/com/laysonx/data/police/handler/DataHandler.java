package com.laysonx.data.police.handler;

/**
 * @description: 清洗待验证数据接口
 * @author: Laysonx
 * @date: 2019/9/26 17:11
 */
public interface DataHandler {

    /**
     * @description 处理特殊数据结构 返回可进行验证的对象
     * @author: Laysonx
     * @date: 2019/9/26 18:20
     * @param t
     * @return: java.lang.Object
     */
    <T> Object getResult(T t);

    /**
     * @description 验证的数据对象
     * @author: Laysonx
     * @date: 2019/9/29 15:35
     * @param
     * @return: java.lang.Class
     */
    Class<?> getTargetClass();

}
