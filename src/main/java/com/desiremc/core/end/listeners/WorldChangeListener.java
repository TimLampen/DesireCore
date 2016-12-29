package com.desiremc.core.end.listeners;

import com.desiremc.core.end.EndModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WorldChangeListener implements Listener{
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if(player.getWorld().getName().equals("world_the_end")){
            player.teleport(EndModule.getInstance().getEndConfig().getEnter());
        }
        else if(event.getFrom().getName().equals("world_the_end")){
            player.teleport(EndModule.getInstance().getEndConfig().getExit());
        }
    }
}
