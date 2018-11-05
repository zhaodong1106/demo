package com.example.demo.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by T011689 on 2018/11/5.
 */
@Component
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri=request.getRequestURI();
        boolean contains = Arrays.stream(uris).anyMatch(uri::equals);
        if(contains){
            return super.preHandle(request, response, handler);
        }
        String name=(String)WebUtils.getSessionAttribute(request,"name");
        if(name==null){
            return false;
        }
        return super.preHandle(request, response, handler);
    }
    private static final String[] uris=new String[]{"/getSession"};
}
