package com.laysonx.data.police.core;

import com.laysonx.data.police.handler.DataHandler;
import com.laysonx.data.police.handler.VerifyHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * @description: 数据信息配置工厂
 * @author: Laysonx
 * @date: 2019/9/27 15:51
 */
@Getter
@Setter
@Deprecated
public class DataVerifyInfoConfigurerFactory implements FactoryBean {

    private Map<Class<?>, VerifyHandler> verifyHelper;

    private Map<Class<?>, DataHandler> dataHelper;

    @Override
    public DataVerifyInfoConfigurer getObject(){

        // 构建 dataVerifyInfoChecker
        DataVerifyInfoChecker dataVerifyInfoChecker = new DataVerifyInfoChecker();
        dataVerifyInfoChecker.setVerifyHelper(verifyHelper);
        dataVerifyInfoChecker.setDataHelper(dataHelper);

        // 构建 config
        return new DataVerifyInfoConfigurer(dataVerifyInfoChecker);
    }

    @Override
    public Class<?> getObjectType() {
        return DataVerifyInfoConfigurer.class;
    }
}
