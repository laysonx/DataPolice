package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.DataHelper;
import com.laysonx.data.police.annotation.DataPoliceHelper;
import com.laysonx.data.police.annotation.VerifyHelper;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class HelperBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public HelperBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    protected void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(DataHelper.class));
        addIncludeFilter(new AnnotationTypeFilter(VerifyHelper.class));
        addIncludeFilter(new AnnotationTypeFilter(DataPoliceHelper.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
}