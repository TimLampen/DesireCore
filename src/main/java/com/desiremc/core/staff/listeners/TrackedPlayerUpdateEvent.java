package com.desiremc.core.staff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Timothy Lampen on 11/19/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class TrackedPlayerUpdateEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private String target;
    private Player player;
    private TrackedPlayerUpdateType update;
    private String arg;
    public TrackedPlayerUpdateEvent(Player player, String target,  TrackedPlayerUpdateType update, String arg){
        this.target = target;
        this.arg = arg;
        this.update = update;
        this.player = player;
    }

    public String getArgument(){
        return arg;
    }

    public String getTarget() {
        return target;
    }

    public Player getPlayer(){
        return player;
    }


    public TrackedPlayerUpdateType getUpdate() {
        return update;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;

    }
}
