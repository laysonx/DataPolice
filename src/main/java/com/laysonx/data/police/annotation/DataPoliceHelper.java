package com.laysonx.data.police.annotation;

import com.laysonx.data.police.core.DataPoliceConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description 开启数据权限入口扫描
 * @author: Laysonx
 * @date: 2019/9/26 17:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(DataPoliceConfigurer.class)
public @interface DataPoliceHelper {


}
