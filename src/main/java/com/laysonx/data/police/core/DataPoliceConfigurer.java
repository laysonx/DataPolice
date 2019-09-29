package com.laysonx.data.police.core;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: Laysonx
 * @date: 2019/9/27 16:18
 */
@Deprecated
public class DataPoliceConfigurer implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{DataVerifyEntranceConfigurer.class.getName()};
    }
}
