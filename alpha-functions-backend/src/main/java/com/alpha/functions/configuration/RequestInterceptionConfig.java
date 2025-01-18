package com.alpha.functions.configuration;

import com.alpha.functions.interceptors.ARequestInterceptor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Data
public class RequestInterceptionConfig implements WebMvcConfigurer {

    private final ARequestInterceptor aRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(aRequestInterceptor);

    }
}
