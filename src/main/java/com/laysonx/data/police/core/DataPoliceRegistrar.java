package com.laysonx.data.police.core;

import com.laysonx.data.police.util.BeanRegistrationUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 组件化
 * @author: Laysonx
 * @date: 2019/9/27 16:18
 */
public class DataPoliceRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        HelperBeanDefinitionScanner scanner = new HelperBeanDefinitionScanner(registry, false);
        scanner.setResourceLoader(resourceLoader);
        scanner.registerFilters();
        //TODO 扫描所有
        scanner.doScan("com");

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoScanner.class.getName(), DataVerifyInfoScanner.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurer.class.getName(), DataVerifyInfoConfigurer.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurerProcessor.class.getName(), DataVerifyInfoConfigurerProcessor.class);
//        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, DataVerifyInfoConfigurerFactory.class.getName(), DataVerifyInfoConfigurerFactory.class);

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
