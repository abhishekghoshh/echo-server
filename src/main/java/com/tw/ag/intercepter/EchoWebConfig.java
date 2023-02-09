package com.tw.ag.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class EchoWebConfig implements WebMvcConfigurer {
    @Autowired
    EchoIntercepter echoIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(echoIntercepter).addPathPatterns("/**");
    }

}
