package com.jason.common.config;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.List;

public class DefaultWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * LoginUserArgumentResolver参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        //argumentResolvers.add(new LoginUserArgumentResolver());
    }

}
