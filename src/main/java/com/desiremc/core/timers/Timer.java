package com.desiremc.core.timers;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class Timer {

    private final long endsAt;
    private String reason;

    /**
     * Create a Timer.
     * @param end How long in milliseconds this timer will run for.
     */
    public Timer(long end, String reason) {
        this.endsAt = System.currentTimeMillis() + end;
        this.reason = reason;
    }

    /**
     *
     * @return The number of milliseconds left. If the time left is inferior to 0, it will return 0.
     */
    public long getTimeLeft() {
        if(getFullTimeLeft() < 0)
            return 0;

        return getFullTimeLeft();
    }

    /**
     *
     * @return The difference between the end of the timer to the current time.
     */
    public long getFullTimeLeft(){
        return getEndsAt() - System.currentTimeMillis();
    }

    /**
     * 
     * @return The time at which the timer ends at.
     */
    public long getEndsAt(){
        return endsAt;
    }

    /**
     *
     * @return The reason of this timer.
     */
    public String getReason() {
        return reason;
    }

}