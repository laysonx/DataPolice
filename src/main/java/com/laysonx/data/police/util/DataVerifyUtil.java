package com.laysonx.data.police.util;

import com.laysonx.data.police.annotation.EnableDataVerify;

/**
 * @description: 数据验证工具
 * ThreadLocal 辅助类
 * 用于 controller service 层验证状态的通知
 * @author: Laysonx
 * @date: 2019/9/26 18:23
 */
public class DataVerifyUtil {

    /** 存储验证状态 */
    private static ThreadLocal<EnableDataVerify> verifyHelper = new ThreadLocal<>();

    /**
     * @description 开启验证
     * @author: Laysonx
     * @date: 2019/9/26 18:25
     * @param clazz EnableDataVerify
     * @return: void
     */
    public static void openVerify(EnableDataVerify clazz) {
        verifyHelper.set(clazz);
    }

    /**
     * @description 是否需要验证
     * @author: Laysonx
     * @date: 2019/9/27 10:27
     * @param
     * @return: java.lang.Boolean
     */
    public static Boolean shouldVerify() {
        return verifyHelper.get() != null;
    }

    /**
     * @description 获取验证对象
     * @author: Laysonx
     * @date: 2019/9/27 10:27
     * @param
     * @return: com.laysonx.data.police.annotation.EnableDataVerify
     */
    public static EnableDataVerify getVerifyTarget() {
        return verifyHelper.get();
    }

    /**
     * @description 关闭ThreadLocal
     * @author: Laysonx
     * @date: 2019/9/27 10:28
     * @param
     * @return: void
     */
    public static void closeVerify() {
        verifyHelper.remove();
    }
}
