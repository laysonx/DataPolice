package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataPolice;
import com.laysonx.data.police.exception.DataVerifyRuleException;
import com.laysonx.data.police.util.BeanRegistrationUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;


/**
 * @description: 注册处理器
 * @author: Laysonx
 * @date: 2019/9/27 16:18
 */
public class DataPoliceRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private final String SCAN_PACKAGES_NOT_ALLOW_NULL_ERROR = "未指定处理器扫描路径";

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableDataPolice.class.getName()));
        String[] scanHelperPackages = attributes.getStringArray("scanHelperPackages");

        // TODO 使用手动配置时 不开启校验
        if (scanHelperPackages == null || scanHelperPackages.length == 0) {
            throw new DataVerifyRuleException(SCAN_PACKAGES_NOT_ALLOW_NULL_ERROR);
        }

        HelperBeanDefinitionScanner scanner = new HelperBeanDefinitionScanner(registry, false);
        scanner.setResourceLoader(resourceLoader);
        scanner.registerFilters();
        scanner.doScan(scanHelperPackages);

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyEntranceConfigurer.class.getName(), DataVerifyEntranceConfigurer.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoChecker.class.getName(), DataVerifyInfoChecker.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurer.class.getName(), DataVerifyInfoConfigurer.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurerProcessor.class.getName(), DataVerifyInfoConfigurerProcessor.class);

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
