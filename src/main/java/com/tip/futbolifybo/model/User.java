package com.tip.futbolifybo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

/**
 * Domain model that represents a user.
 *
 * @author cassiomolin
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "lastlogin")
    private Timestamp lastLogin;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Set<Authority> authorities;

    public User() {}

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
