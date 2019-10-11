package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataPolice;
import com.laysonx.data.police.exception.DataVerifyRuleException;
import com.laysonx.data.police.util.BeanRegistrationUtil;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @description: 注册处理器
 * @author: Laysonx
 * @date: 2019/9/27 16:18
 */
public class DataPoliceRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private final String SCAN_PACKAGES_NOT_ALLOW_NULL_ERROR = "未指定处理器扫描路径";

    private final String POINTCUT_NOT_ALLOW_NULL_ERROR = "未初始化@EnableDataPolice注解pointcut表达式";

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableDataPolice.class.getName()));
        String[] scanHelperPackages = attributes.getStringArray("scanHelperPackages");
        String pointcut = attributes.getString("pointcut");

        // TODO 使用手动配置时 不开启校验
        if (scanHelperPackages == null || scanHelperPackages.length == 0) {
            throw new DataVerifyRuleException(SCAN_PACKAGES_NOT_ALLOW_NULL_ERROR);
        }
        if (StringUtils.isEmpty(pointcut)) {
            throw new DataVerifyRuleException(POINTCUT_NOT_ALLOW_NULL_ERROR);
        }

        HelperBeanDefinitionScanner scanner = new HelperBeanDefinitionScanner(registry);
        scanner.setResourceLoader(resourceLoader);
        scanner.registerFilters();
        scanner.doScan(scanHelperPackages);

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurer.class.getName(), DataVerifyInfoConfigurer.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyEntranceConfigurer.class.getName(), DataVerifyEntranceConfigurer.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoChecker.class.getName(), DataVerifyInfoChecker.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurerProcessor.class.getName(), DataVerifyInfoConfigurerProcessor.class);
        Map<String, Object> property = this.initDataVerifyAdvisorProperty(pointcut);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, AspectJExpressionPointcutAdvisor.class.getName(), AspectJExpressionPointcutAdvisor.class,property);

    }

    /**
     * @description 初始化空 advisor
     * 装配过程中会替换 advice
     * @author: Laysonx
     * @date: 2019/10/11 13:16
     * @param pointcut
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> initDataVerifyAdvisorProperty(String pointcut) {
        Advice nullAdvice = (MethodInterceptor) invocation -> {
            Object obj = invocation.proceed();
            return obj;
        };

        Map<String,Object> property = new HashMap<>(2);
        property.put("expression", pointcut);
        property.put("advice", nullAdvice);
        return property;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
