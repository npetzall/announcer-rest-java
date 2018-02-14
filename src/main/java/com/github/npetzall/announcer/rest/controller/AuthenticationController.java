package com.github.npetzall.announcer.rest.controller;

import com.github.npetzall.announcer.rest.domain.AuthenticationDetails;
import com.github.npetzall.announcer.rest.jwt.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AuthenticationManager authenticationManager;
    private Factory factory;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, Factory factory) {
        this.authenticationManager = authenticationManager;
        this.factory = factory;
    }

    @RequestMapping(
            path = "${auth.path.status}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<?> status() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.ok("authenticated as " + authentication.getPrincipal());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not authenticated");
        }
    }

    @RequestMapping(
            path = "${auth.path.claim}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<?> claim(@RequestBody final AuthenticationDetails authenticationDetails) {
        LOGGER.info("Claim");
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDetails.getUser(), authenticationDetails.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(factory.newToken(authenticationDetails.getUser()));
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
            path = "${auth.path.revoke}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> revoke() {
        LOGGER.info("Revoke");
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(
            path = "${auth.path.renew}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> renew() {
        LOGGER.info("Renew");
        return ResponseEntity.notFound().build();
    }
}
