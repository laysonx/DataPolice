package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataVerify;
import com.laysonx.data.police.exception.DataVerifyFailException;
import com.laysonx.data.police.exception.DataVerifyRuleException;
import com.laysonx.data.police.handler.DataHandler;
import com.laysonx.data.police.handler.VerifyHandler;
import com.laysonx.data.police.util.DataVerifyUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description: 数据扫描器
 * @author: Laysonx
 * @date: 2019/9/27 10:58
 */
@Getter
@Setter
@Slf4j
public class DataVerifyInfoScanner {

    /** 存放验证对象 以及其 验证方式 */
    private Map<Class<?>, VerifyHandler> verifyHelper;

    /** 存放验证对象 以及其 清洗方式 */
    private Map<Class<?>, DataHandler> dataHelper;

    public Object around(ProceedingJoinPoint jp) throws Throwable {

        Object result = jp.proceed();
        Object resultObject = result;

        if (DataVerifyUtil.shouldVerify()) {
            if (resultObject != null) {

                // 获取数据验证注解
                EnableDataVerify enableDataVerify = DataVerifyUtil.getVerifyTarget();
                // 获取注解传递的 验证所需的接口类型
                Class<?>[] value = enableDataVerify.value();

                // 返回数据的结构不同，根据情况处理返回数据
                if (dataHelper != null && dataHelper.size() > 0) {
                    for (Class<?> resultClass : dataHelper.keySet()) {
                        if (resultClass.isInstance(resultObject)) {
                            DataHandler resultHandler = dataHelper.get(resultClass);
                            if (resultHandler != null) {
                                resultObject = resultHandler.getResult(resultObject);
                            }
                        }
                    }
                }

                if (value != null && value.length > 0) {
                    for (Class<?> authClass : value) {
                        // 处理集合
                        if (resultObject instanceof List) {
                            Collection collection = (Collection) resultObject;
                            for (Object obj : collection) {
                                authResultItem(obj, authClass);
                            }
                        } else {
                            authResultItem(resultObject, authClass);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 验证数据
     *
     * @Author lixin
     * @Date 2018/3/30
     * @params
     * @return:
     */
    private void authResultItem(Object resultObject, Class<?> authClass) {

        // 验证
        if (authClass.isInstance(resultObject)) {
            VerifyHandler verifyHandler = verifyHelper.get(authClass);
            if (verifyHandler != null) {
                if (!verifyHandler.verify(authClass.cast(resultObject))) {
                    throw new DataVerifyFailException("无此数据权限");
                }
            }
        }
    }
}
