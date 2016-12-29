package com.desiremc.core.end;

import com.desiremc.core.DesireCore;
import com.desiremc.core.end.listeners.EntitySpawnListener;
import com.desiremc.core.end.listeners.WorldChangeListener;
import com.desiremc.core.module.Module;
import org.bukkit.Bukkit;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class EndModule extends Module {
    private static EndModule instance;
    private EndConfig config;

    public EndModule(){
        instance = this;
    }
    @Override
    public void onLoad() {
        config = new EndConfig();
    }

    @Override
    public void onEnable() {
        registerListeners(new WorldChangeListener(), new EntitySpawnListener());
    }

    @Override
    public void onDisable() {

    }

    public static EndModule getInstance(){
        return instance;
    }

    public EndConfig getEndConfig(){
        return config;
    }
}
