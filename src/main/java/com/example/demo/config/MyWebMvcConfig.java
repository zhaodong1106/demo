package com.example.demo.config;

import com.example.demo.Interceptor.MyHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by T011689 on 2018/11/5.
 */
@EnableWebMvc
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer{
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
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+imageFile);
    }
}
