package com.github.npetzall.announcer.rest.jwt;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException(String subject) {
        super("Authentication failed for subject:"+subject);
    }
}
