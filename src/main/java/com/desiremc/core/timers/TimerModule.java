package com.desiremc.core.timers;

import com.desiremc.core.module.Module;

/**
 * Created by Sneling on 21/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class TimerModule extends Module {

    private static TimerModule instance;

    private TimerHandler timerHandler;

    public TimerModule() {
        instance = this;

        timerHandler = new TimerHandler();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static TimerModule getInstance() {
        return instance;
    }

    public TimerHandler getTimerHandler() {
        return timerHandler;
    }
}