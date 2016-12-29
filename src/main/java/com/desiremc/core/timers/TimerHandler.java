package com.desiremc.core.timers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class TimerHandler {

    private HashMap<UUID, List<Timer>> currentTimers = new HashMap();

    /**
     * Note: This will erase any current timer with the same reason
     * @param uuid
     * @param reason
     * @param time
     * @return
     */
    public void createTimer(UUID uuid, String reason, long time){
        removeTimer(uuid, reason);

        Timer timer = new Timer(time, reason);
        List<Timer> timers = currentTimers.get(uuid);

        if(timers == null)
            timers = new ArrayList<>();

        timers.add(timer);
        currentTimers.put(uuid, timers);
    }

    public boolean hasTimerFor(UUID uuid, String reason){
        return getTimer(uuid, reason) != null;
    }

    public List<Timer> getTimers(UUID uuid){
        return currentTimers.get(uuid);
    }

    public Timer getTimer(UUID uuid, String reason){
        if(!hasTimerFor(uuid, reason))
            return null;

        for(Timer timer: currentTimers.get(uuid))
            if (timer.getReason().equalsIgnoreCase(reason))
                return timer;

        return null;
    }

    public void removeTimer(UUID uuid, String reason){
        if(!hasTimerFor(uuid, reason))
            return;

        for(Timer timer: currentTimers.get(uuid))
            if(timer.getReason().equalsIgnoreCase(reason))
                //noinspection Since15
                currentTimers.remove(uuid, timer);
    }


    public void clean(){
        for(UUID uuid: currentTimers.keySet())
            for(Timer timer: currentTimers.get(uuid))
                if(timer.getTimeLeft() == 0)
                    // noinspection Since15
                    currentTimers.remove(uuid, timer);
    }

}