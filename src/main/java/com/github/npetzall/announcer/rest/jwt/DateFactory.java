package com.github.npetzall.announcer.rest.jwt;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateFactory {

    private final Clock clock;

    public DateFactory(Clock clock) {
        this.clock = clock;
    }

    public Date now() {
        return Date.from(Instant.now(clock));
    }

    public Date daysInFuture(int days) {
        return Date.from(Instant.now(clock).plus(days, ChronoUnit.DAYS));
    }

    public boolean isValid(Date expirationDate) {
        return expirationDate.after(now());
    }
}
