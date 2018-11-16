package com.example.demo.config;

import com.example.demo.Interceptor.MyHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

/**
 * Created by T011689 on 2018/11/5.
 */
//@EnableWebMvc
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private MyHandlerInterceptor myHandlerInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(myHandlerInterceptor);
//    }
    @Value("${web.img-path}")
    private String imageFile;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS));
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+imageFile).setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS));;
    }

    @Bean
    public ShallowEtagHeaderFilter hallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
