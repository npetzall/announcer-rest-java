package com.github.npetzall.announcer.rest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.*;

public class WorkLogEntry {
    private final LocalDateTime timeStamp;
    private final String user;
    private final String what;
    private final String why;

    @JsonCreator
    public WorkLogEntry(
            @JsonProperty("user") String user,
            @JsonProperty("what") String what,
            @JsonProperty("why") String why
    ) {
        this.timeStamp = LocalDateTime.now(Clock.systemUTC());
        this.user = user;
        this.what = what;
        this.why = why;
    }

    @JsonCreator
    public WorkLogEntry(
            @JsonProperty("timeStamp") LocalDateTime timeStamp,
            @JsonProperty("user") String user,
            @JsonProperty("what") String what,
            @JsonProperty("why") String why
    ) {
        this.timeStamp = timeStamp;
        this.user = user;
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
