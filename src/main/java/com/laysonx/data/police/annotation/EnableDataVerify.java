package com.laysonx.data.police.annotation;

import java.lang.annotation.*;

/**
 * @description 开启验证
 * e.g. @EnableDataVerify(value = {BaseEntity.class})
 * @author: Laysonx
 * @date: 2019/9/26 17:24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableDataVerify {

    /**
     * 需要验证数据对象集合
     */
    Class<?>[] value();



}
