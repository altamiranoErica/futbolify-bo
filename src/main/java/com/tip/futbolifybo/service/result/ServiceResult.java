package com.tip.futbolifybo.service.result;

public class ServiceResult {

    private Integer code;
    private String title;
    private String message;

    public ServiceResult(Integer code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
