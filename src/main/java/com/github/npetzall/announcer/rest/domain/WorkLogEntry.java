package com.github.npetzall.announcer.rest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.*;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;

public class WorkLogEntry {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timeStamp;
    private final String user;
    private final String what;
    private final String why;

    @JsonCreator
    public WorkLogEntry(
            @JsonProperty("timeStamp") LocalDateTime timeStamp,
            @JsonProperty("user") String user,
            @JsonProperty("what") String what,
            @JsonProperty("why") String why
    ) {

        this.timeStamp = Objects.nonNull(timeStamp) ? timeStamp: LocalDateTime.now(Clock.systemUTC());
        this.user = Objects.nonNull(user) && !user.trim().isEmpty() ? user : SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        this.what = what;
        this.why = why;
    }

    /**
     * Getter for property 'timeStamp'.
     *
     * @return Value for property 'timeStamp'.
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
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
     * Getter for property 'what'.
     *
     * @return Value for property 'what'.
     */
    public String getWhat() {
        return what;
    }

    /**
     * Getter for property 'why'.
     *
     * @return Value for property 'why'.
     */
    public String getWhy() {
        return why;
    }
}
