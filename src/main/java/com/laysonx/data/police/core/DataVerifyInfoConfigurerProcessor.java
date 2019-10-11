package com.laysonx.data.police.core;

import com.laysonx.data.police.exception.DataVerifyRuleException;
import com.laysonx.data.police.handler.DataHandler;
import com.laysonx.data.police.handler.VerifyHandler;
import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 装配数据清理、数据验证处理器
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
public class DataVerifyInfoConfigurerProcessor implements BeanPostProcessor, ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof DataVerifyInfoConfigurer) {
            /** 从容器中获取 DataVerifyInfoChecker */
            DataVerifyInfoChecker infoChecker = context.getBean(DataVerifyInfoChecker.class);
            /** 从容器中获取用户自定义的 VerifyHandler 子类 */
            Map<String, VerifyHandler> verifyHandlerMap = context.getBeansOfType(VerifyHandler.class);
            /** 组装 verifyHelper 用于数据校验 */
            Map<Class<?>, VerifyHandler> verifyHelper = new HashMap<>(verifyHandlerMap.size());
            for (VerifyHandler handler : verifyHandlerMap.values()) {
                Class targetClass = handler.getTargetClass();
                if(Objects.isNull(targetClass)){
                    throw new DataVerifyRuleException(handler.getClass().getName()+"实现getTargetClass()方法返回值无意义：return null");
                }
                if(verifyHelper.containsKey(targetClass)){
                    throw new DataVerifyRuleException("VerifyHelper组装异常：存在一个"+targetClass+"对应多个VerifyHandler");
                }
                verifyHelper.put(targetClass, handler);
            }

            /** 从容器中获取用户自定义的 DataHandler 子类 */
            Map<String, DataHandler> dataHandlerMap = context.getBeansOfType(DataHandler.class);
            /** 组装 dataHelper 用于数据清洗 */
            Map<Class<?>, DataHandler> dataHelper = new HashMap<>(dataHandlerMap.size());
            for (DataHandler handler : dataHandlerMap.values()) {
                Class targetClass = handler.getTargetClass();
                if(Objects.isNull(targetClass)){
                    throw new DataVerifyRuleException(handler.getClass().getName()+"实现getTargetClass()方法返回值无意义：return null");
                }
                if(dataHelper.containsKey(targetClass)){
                    throw new DataVerifyRuleException("DataHelper组装异常：存在一个"+targetClass+"对应多个DataHandler");
                }
                dataHelper.put(targetClass, handler);
            }

            /** 装配用户自定义 Helper 到 DataVerifyInfoChecker */
            infoChecker.setVerifyHelper(verifyHelper);
            infoChecker.setDataHelper(dataHelper);
            ((DataVerifyInfoConfigurer) bean).setInfoChecker(infoChecker);

            /** 获取数据检查切面 */
            AspectJExpressionPointcutAdvisor advisor = context.getBean(AspectJExpressionPointcutAdvisor.class);
            /** 替换 advice */
            advisor.setAdvice((Advice) bean);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
