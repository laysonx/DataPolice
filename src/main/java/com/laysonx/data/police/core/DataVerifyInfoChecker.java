package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataVerify;
import com.laysonx.data.police.exception.DataVerifyFailException;
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
 * @description: 数据检查
 * @author: Laysonx
 * @date: 2019/9/27 10:58
 */
@Getter
@Setter
@Slf4j
public class DataVerifyInfoChecker {

    /** 存放验证对象 以及其 验证方式 */
    private Map<Class<?>, VerifyHandler> verifyHelper;

    /** 存放验证对象 以及其 清洗方式 */
    private Map<Class<?>, DataHandler> dataHelper;

    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Object result = jp.proceed();
        Object waitVerifyData = result;
        if (DataVerifyUtil.shouldVerify()) {
            if (waitVerifyData != null) {
                // 获取数据验证注解
                EnableDataVerify enableDataVerify = DataVerifyUtil.getVerifyTarget();
                // 获取注解传递的 验证所需的接口类型
                Class<?>[] value = enableDataVerify.value();
                String errorMessage = enableDataVerify.errorMessage();
                // 返回数据的结构不同，根据情况处理返回数据
                if (dataHelper != null && dataHelper.size() > 0) {
                    for (Class<?> specialClass : dataHelper.keySet()) {
                        if (specialClass.isInstance(waitVerifyData)) {
                            DataHandler dataHandler = dataHelper.get(specialClass);
                            if (dataHandler != null) {
                                waitVerifyData = dataHandler.getResult(waitVerifyData);
                            }
                        }
                    }
                }

                // 检查数据
                if (value != null && value.length > 0) {
                    for (Class<?> waitVerifyClass : value) {
                        // 处理集合
                        if (waitVerifyData instanceof List) {
                            Collection dataCollection = (Collection) waitVerifyData;
                            for (Object data : dataCollection) {
                                this.verifyData(data, waitVerifyClass,errorMessage);
                            }
                        } else {
                            this.verifyData(waitVerifyData, waitVerifyClass,errorMessage);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @description 验证数据
     * @author: Laysonx
     * @date: 2019/10/8 15:44
     * @param waitVerifyData
     * @param waitVerifyClass
     * @param errorMassage
     * @return: void
     */
    private void verifyData(Object waitVerifyData, Class<?> waitVerifyClass,String errorMassage) {
        if (waitVerifyClass.isInstance(waitVerifyData)) {
            VerifyHandler verifyHandler = verifyHelper.get(waitVerifyClass);
            if (verifyHandler != null) {
                if (!verifyHandler.verify(waitVerifyClass.cast(waitVerifyData))) {
                    throw new DataVerifyFailException(errorMassage);
                }
            }
        }
    }
}
