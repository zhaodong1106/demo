package com.example.demo.jedis;

/**
 * Created by T011689 on 2018/10/10.
 */
public enum Status {
    INVALID_REQUEST(400,"400 error"),INNER_SERVER_ERROR(500,"inner server error"),
    SUCCESS(200,"success");
    private int code;
    private String message;
    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
