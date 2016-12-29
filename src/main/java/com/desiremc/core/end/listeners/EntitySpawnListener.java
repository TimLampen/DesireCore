package com.desiremc.core.end.listeners;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        Entity entity  = event.getEntity();
        if(entity.getWorld().getName().equals("world_the_end")){
            if(!(event.getSpawnReason()== CreatureSpawnEvent.SpawnReason.SPAWNER || event.getSpawnReason()== CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                event.setCancelled(true);
            }
        }
    }
}
