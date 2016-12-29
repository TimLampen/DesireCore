package com.desiremc.core.wrench.listeners;

import com.desiremc.core.DesireCore;
import de.slikey.effectlib.effect.ExplodeEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WrenchSpawnerPlaceListener implements Listener{

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        ItemStack is = event.getItemInHand();
        Block b = event.getBlock();
        if(is!=null &&is.getType()== Material.MOB_SPAWNER && is.hasItemMeta() && is.getItemMeta().getDisplayName().contains("Spawner")){
            CreatureSpawner spawner = (CreatureSpawner)b.getState();
            spawner.setSpawnedType(EntityType.valueOf(ChatColor.stripColor(is.getItemMeta().getDisplayName()).split(" ")[0].toUpperCase()));
            ExplodeEffect effect = new ExplodeEffect(DesireCore.getInstance().getEffectManager());
            effect.setLocation(spawner.getLocation());
            effect.start();
        }
    }
}
