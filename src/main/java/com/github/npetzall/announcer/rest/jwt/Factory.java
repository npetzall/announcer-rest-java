package com.github.npetzall.announcer.rest.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Factory {

    @Autowired
    private DateFactory dateFactory;

    @Value("${auth.valid_days}")
    private int validDays;

    @Value("${auth.secret}")
    private String secret;


    public String newToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(dateFactory.now())
                .setExpiration(dateFactory.daysInFuture(validDays))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
