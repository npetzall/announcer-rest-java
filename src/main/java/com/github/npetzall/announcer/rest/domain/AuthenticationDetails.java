package com.github.npetzall.announcer.rest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationDetails {

    private final String user;
    private final String password;

    @JsonCreator
    public AuthenticationDetails(@JsonProperty("user") String user,@JsonProperty("password") String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Getter for property 'user'.
     *
     * @return Value for property 'user'.
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for property 'password'.
     *
     * @return Value for property 'password'.
     */
    public String getPassword() {
        return password;
    }
}
