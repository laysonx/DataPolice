package com.laysonx.data.police.core;

import com.laysonx.data.police.annotation.EnableDataVerify;
import com.laysonx.data.police.util.DataVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description: 入口扫描器
 * 检查web接口上是否存在@EnableDataVerify
 * 存在则说明改接口下的数据需要校验，则开启验证
 * @author: Laysonx
 * @date: 2019/9/27 10:32log
 */
@Slf4j
public class DataVerifyEntranceScanner extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获得当前方法
            Method method = handlerMethod.getMethod();
            // 检查是否需要开启校验
            EnableDataVerify enableDataVerify = method.getAnnotation(EnableDataVerify.class);
            if (enableDataVerify != null) {
                log.debug("DataPolice 监控到接口 {} 需要验证数据",method.getName());
                //TODO 检查验证的目标是否初始化
                DataVerifyUtil.openVerify(enableDataVerify);
            } else {
                log.debug("DataPolice 监控到接口 {} 不需要验证数据",method.getName());
            }
        }

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        DataVerifyUtil.closeVerify();
    }
}
