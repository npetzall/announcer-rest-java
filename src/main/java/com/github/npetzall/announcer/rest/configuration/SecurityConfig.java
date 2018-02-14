package com.github.npetzall.announcer.rest.configuration;

import com.github.npetzall.announcer.rest.jwt.AuthorizationFilter;
import com.github.npetzall.announcer.rest.jwt.DateFactory;
import com.github.npetzall.announcer.rest.jwt.NotAuthenticatedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.time.Clock;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private NotAuthenticatedEntryPoint notAuthenticatedEntryPoint;

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }

    @Bean
    public DateFactory jwtDateFactory() {
        return new DateFactory(Clock.systemUTC());
    }

    @Value("${auth.path.claim}")
    private String claim_url;

    @Value("${auth.path.status}")
    private String status_url;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(notAuthenticatedEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(POST, claim_url).permitAll()
                .antMatchers(GET, status_url).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authorizationFilter(), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("testUser")
                .password("testPassword")
                .roles("USER");
    }
}
