package com.desiremc.core.enderchest;

import com.desiremc.core.DesireCore;
import com.desiremc.core.enderchest.commands.EnderChestCommand;
import com.desiremc.core.enderchest.listeners.EnderChestOpenListener;
import com.desiremc.core.module.Module;

/**
 * Created by Timothy Lampen on 11/15/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class EnderChestModule extends Module{

    private static EnderChestModule instance;

    public EnderChestModule(){
        instance = this;
    }
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        DesireCore.registerListeners(new EnderChestOpenListener());
        registerCommand(new EnderChestCommand());
    }

    @Override
    public void onDisable() {

    }


    private boolean isDisabled = true;

    public boolean getDisable(){
        return isDisabled;
    }

    public void setDisabled(boolean b){
        isDisabled = b;
    }

    public static EnderChestModule getInstance(){
        return instance;
    }

}
