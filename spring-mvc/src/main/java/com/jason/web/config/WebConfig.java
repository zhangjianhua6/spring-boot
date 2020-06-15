package com.jason.web.config;

import com.jason.web.aop.AuthorizationInterceptor;
import com.jason.web.aop.LoginUserMethodArgumentResolver;
import com.jason.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: zhangjianhua
 * @date: 2019-12-07 15:04
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public AuthorizationInterceptor authorizationInterceptor(JwtUtils jwtUtils){
        return new AuthorizationInterceptor(jwtUtils);
    }

    @Bean
    public LoginUserMethodArgumentResolver loginUserMethodArgumentResolver(JwtUtils jwtUtils){
        return new LoginUserMethodArgumentResolver(jwtUtils);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor(jwtUtils)).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserMethodArgumentResolver(jwtUtils));
    }
}
