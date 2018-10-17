package com.example.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by T011689 on 2018/10/15.
 */
@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(OrderError.class)
    public ModelAndView orderErrorHandler(OrderError ex){
        return new ModelAndView("goods/error").addObject("message",ex.getMessage());
    }
}
