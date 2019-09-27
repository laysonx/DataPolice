package com.laysonx.data.police.core;

import com.laysonx.data.police.handler.DataHandler;
import com.laysonx.data.police.handler.VerifyHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * @description: 内容扫描器FactoryBean
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
@Getter
@Setter
public class DataVerifyInfoConfigurerFactory implements FactoryBean {

    private Map<Class<?>, VerifyHandler> verifyHelper;

    private Map<Class<?>, DataHandler> dataHelper;

    @Override
    public Object getObject(){

        // 构建 scanner
        DataVerifyInfoScanner dataVerifyInfoScanner = new DataVerifyInfoScanner();
        dataVerifyInfoScanner.setVerifyHelper(verifyHelper);
        dataVerifyInfoScanner.setDataHelper(dataHelper);

        // 构建 config
        return new DataVerifyInfoConfigurer(dataVerifyInfoScanner);
    }

    @Override
    public Class<?> getObjectType() {
        return DataVerifyInfoConfigurer.class;
    }
}
