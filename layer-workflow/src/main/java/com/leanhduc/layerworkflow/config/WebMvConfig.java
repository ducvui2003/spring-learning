package com.leanhduc.layerworkflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvConfig implements WebMvcConfigurer {
    private final InterceptorLogging interceptorLogging;

    public WebMvConfig(InterceptorLogging interceptorLogging) {
        this.interceptorLogging = interceptorLogging;
    }

    // Register Interceptors here, Can not register via @Bean
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorLogging).addPathPatterns(("/**"));
    }
}
