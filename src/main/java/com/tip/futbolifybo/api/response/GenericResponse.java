package com.tip.futbolifybo.api.response;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GenericResponse {

    private String title;
    private String message;

    public GenericResponse(String title, String message) {
        this.title = title;
        this.message = message;
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
