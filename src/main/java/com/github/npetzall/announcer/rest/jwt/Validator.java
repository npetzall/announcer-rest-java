package com.github.npetzall.announcer.rest.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    private DateFactory dateFactory;

    @Value("${auth.secret}")
    private String secret;


    public TokenFacade validate(String compactJws) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(compactJws);
        if (dateFactory.isValid(claimsJws.getBody().getExpiration())){
            return new TokenFacade(claimsJws);
        };
        throw new TokenExpiredException(claimsJws.getBody().getSubject());
    }
}
