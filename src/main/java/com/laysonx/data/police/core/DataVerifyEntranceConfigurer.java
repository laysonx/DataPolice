package com.laysonx.data.police.core;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description: 配置启动入口扫描器
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
public class DataVerifyEntranceConfigurer extends WebMvcConfigurerAdapter {

    //TODO 自定义配置路径

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataVerifyEntranceScanner()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
