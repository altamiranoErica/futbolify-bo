package com.tip.futbolifybo.security.api.model;

/**
 * API model user credentials.
 *
 * @author cassiomolin
 */
public class UserCredentials {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}