package com.tip.futbolifybo.api.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserResponse {

    private String token;

    public UserResponse() { }

    public UserResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
