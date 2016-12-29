package com.desiremc.core.combatlogger;

import com.desiremc.core.module.Module;

/**
 * Created by Sneling on 25/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class CombatLoggerModule extends Module {

    private static CombatLoggerModule instance;

    private CombatLoggerManager manager;

    public CombatLoggerModule() {
        instance = this;

        manager = new CombatLoggerManager();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        registerListeners(new CombatLogListener());
        registerCommand(new LogoutCommand());
    }

    @Override
    public void onDisable() {

    }

    public static CombatLoggerModule getInstance() {
        return instance;
    }

    public CombatLoggerManager getManager() {
        return manager;
    }
}