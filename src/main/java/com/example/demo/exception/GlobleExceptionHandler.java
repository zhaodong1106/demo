package com.example.demo.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Raytine on 2018/10/13.
 */
@ControllerAdvice
public class GlobleExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @ExceptionHandler(DulplidateException.class)
    @ResponseBody
    public Object dulplidateExceptionHandler(DulplidateException ex){
        String message = ex.getMessage();
        return message;
    }

}
