package com.peng.common.pojo;

public class ResponseData<T> {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回描述
     */
    private String message;

    private T data;

    public ResponseData() {
    }

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
