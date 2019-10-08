package com.laysonx.data.police.core;

import com.laysonx.data.police.exception.DataVerifyRuleException;
import com.laysonx.data.police.handler.DataHandler;
import com.laysonx.data.police.handler.VerifyHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 装配数据清理、数据验证处理器
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
public class DataVerifyInfoConfigurerProcessor implements BeanPostProcessor, ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataVerifyInfoConfigurer) {

            DataVerifyInfoScanner infoScanner = context.getBean(DataVerifyInfoScanner.class);
            Map<String, VerifyHandler> verifyHandlerMap = context.getBeansOfType(VerifyHandler.class);
            Map<Class<?>, VerifyHandler> verifyHelper = new HashMap<>(verifyHandlerMap.size());

            for (VerifyHandler handler : verifyHandlerMap.values()) {
                Class targetClass = handler.getTargetClass();
                if(verifyHelper.containsKey(targetClass)){
                    throw new DataVerifyRuleException("VerifyHelper组装异常：存在一个"+targetClass+"对应多个VerifyHandler");
                }
                verifyHelper.put(targetClass, handler);
            }

            Map<String, DataHandler> dataHandlerMap = context.getBeansOfType(DataHandler.class);
            Map<Class<?>, DataHandler> dataHelper = new HashMap<>(dataHandlerMap.size());
            for (DataHandler handler : dataHandlerMap.values()) {
                Class targetClass = handler.getTargetClass();
                if(dataHelper.containsKey(targetClass)){
                    throw new DataVerifyRuleException("DataHelper组装异常：存在一个"+targetClass+"对应多个DataHandler");
                }
                dataHelper.put(targetClass, handler);
            }

            infoScanner.setVerifyHelper(verifyHelper);
            infoScanner.setDataHelper(dataHelper);
            ((DataVerifyInfoConfigurer) bean).setInfoScanner(infoScanner);

        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
