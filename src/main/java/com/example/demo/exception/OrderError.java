package com.example.demo.exception;

/**
 * Created by T011689 on 2018/10/15.
 */
public class OrderError extends RuntimeException {
    public OrderError(String message) {
        super(message);
    }
}
