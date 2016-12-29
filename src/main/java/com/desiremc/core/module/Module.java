package com.desiremc.core.module;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import org.bukkit.event.Listener;

/**
 * Created by Sneling on 16/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public abstract class Module {

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    protected void registerCommand(DesireCommand command){
        DesireCore.getInstance().registerCommand(command);
    }

    protected void registerListeners(Listener... listeners){
        DesireCore.registerListeners(listeners);
    }

}