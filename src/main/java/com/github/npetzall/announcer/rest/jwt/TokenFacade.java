package com.github.npetzall.announcer.rest.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class TokenFacade {

    private final Jws<Claims> claimsJws;

    public TokenFacade(Jws<Claims> claimsJws) {
        this.claimsJws = claimsJws;
    }

    public String getSubject() {
        return claimsJws.getBody().getSubject();
    }
}
