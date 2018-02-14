package com.github.npetzall.announcer.rest.jwt;

import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.Objects;

import static com.github.npetzall.announcer.rest.jwt.Constants.TOKEN_PREFIX;

public class AuthorizationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private Validator validator;

    @Value("${auth.header}")
    private String header;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(header);

        if (Objects.nonNull(headerValue) && !headerValue.trim().isEmpty() && headerValue.startsWith(TOKEN_PREFIX)) {
            String compactJws = headerValue.substring(TOKEN_PREFIX.length());
            try {
                TokenFacade tokenFacade = validator.validate(compactJws);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenFacade.getSubject(), null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug("authenticated user " + tokenFacade.getSubject() + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (SignatureException e) {
                LOGGER.error("Invalid token", e);
            } catch (TokenExpiredException e) {
                LOGGER.error("Expired", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
