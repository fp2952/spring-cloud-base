package com.peng.auth.api.pojo;

public enum ResponseCode {

    SUCCESS(1200, "success."),
    ERROR(1201, "error."),
    PARA_ERROR(1202, "parameters error.");

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
