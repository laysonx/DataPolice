package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.DataPoliceHelper;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @description: 扫描注册数据处理器（DataHandler、VerifyHandler）
 * @author: Laysonx
 * @date: 2019/9/27 10:58
 */
public class HelperBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public HelperBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    protected void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(DataPoliceHelper.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
}