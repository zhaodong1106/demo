package com.example.demo.base;

/**
 * Created by T011689 on 2018/11/20.
 */
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    private ApiResponse() {
    }

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse ofSuccess(Object data){
        return new ApiResponse(Status.SUUCESS.code,Status.SUUCESS.message,data);
    }
    public ApiResponse ofStatus(Status status){
        return new ApiResponse(status.code,status.message);
    }
    public static  enum  Status{
        SUUCESS(200,"success"),REQUEST_ERROR(400,"request_error"),
        INTERNAL_SERVER_ERROR(500,"internal_server_error"),NOT_LOGIN(600,"not login");

        private int code;
        private String message;

        Status(int code,String message){
            this.code=code;
            this.message=message;
        }
    }
}
