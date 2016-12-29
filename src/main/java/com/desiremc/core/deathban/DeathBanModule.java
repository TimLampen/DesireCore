package com.desiremc.core.deathban;

import com.desiremc.core.DesireCore;
import com.desiremc.core.deathban.listeners.DeathListener;
import com.desiremc.core.deathban.listeners.LoginListener;
import com.desiremc.core.module.Module;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class DeathBanModule extends Module {

    private static DeathBanModule instance;

    private DeathBanAccountManager accountManager;

    public DeathBanModule() {
        instance = this;
        this.accountManager = new DeathBanAccountManager();
    }

    public DeathBanAccountManager getAccountManager() {
        return accountManager;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        DesireCore.registerListeners(
                new LoginListener(),
                new DeathListener());
    }

    @Override
    public void onDisable() {

    }

    public static DeathBanModule getInstance() {
        return instance;
    }

}