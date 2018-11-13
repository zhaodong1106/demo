package com.example.demo.base;

/**
 * Created by Raytine on 2018/11/13.
 */
public class BaseResult {
    private Boolean success;
    private Object data;
    private String message;

    public BaseResult() {
    }

    public BaseResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResult(Boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
