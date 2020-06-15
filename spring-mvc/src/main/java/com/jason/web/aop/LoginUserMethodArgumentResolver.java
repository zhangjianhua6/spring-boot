package com.jason.web.aop;

import com.jason.web.annotation.LoginUser;
import com.jason.web.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private JwtUtils jwtUtils;

    public LoginUserMethodArgumentResolver(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if(methodParameter.hasParameterAnnotation(LoginUser.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        LoginUser loginUserAnnotation = methodParameter.getParameterAnnotation(LoginUser.class);
        HttpServletRequest request= (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String accessToken = request.getHeader(jwtUtils.getHeader());
        if(StringUtils.isEmpty(accessToken)){
            return null;
        }
        jwtUtils.getClaimByToken(accessToken);
        return null;
    }
}
