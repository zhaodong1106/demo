package com.example.demo.enums;

/**
 * Created by T011689 on 2018/12/7.
 */
public enum Status {
    INVALID_REQUEST(400,"Bad Request"),
    INNER_SERVER_ERROR(500,"inner server error"),
    SUCCESS(200,"success"),
    NOT_LOGIN(401,"NOT LOGIN"),
    FORBIDEN_ACCESS(403,"forbidden access");
    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
