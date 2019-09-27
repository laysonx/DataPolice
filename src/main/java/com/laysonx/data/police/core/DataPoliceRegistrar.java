package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataPolice;
import com.laysonx.data.police.util.BeanRegistrationUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 组件化
 * @author: Laysonx
 * @date: 2019/9/27 16:18
 */
public class DataPoliceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableDataPolice.class.getName()));
        String[] namespaces = attributes.getStringArray("value");
        int order = (Integer)attributes.getNumber("order");

        // 注册扫描器

    }
}
