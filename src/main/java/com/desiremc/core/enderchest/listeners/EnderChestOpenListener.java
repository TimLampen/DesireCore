package com.desiremc.core.enderchest.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.crates.KeyModule;
import com.desiremc.core.enderchest.EnderChestModule;
import com.desiremc.core.lang.Lang;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Timothy Lampen on 11/16/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class EnderChestOpenListener implements Listener {

    @EventHandler
    public void onOpen(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction()== Action.RIGHT_CLICK_BLOCK && event.getClickedBlock()!=null){
            Block b = event.getClickedBlock();
            if(b.getType()== Material.ENDER_CHEST){
                if(EnderChestModule.getInstance().getDisable() && !KeyModule.getInstance().isKeyChest(b)){
                    event.setCancelled(true);
                    player.sendMessage(Lang.ENDMANAGEMENT_DENY_OPEN);
                }
            }
        }
    }
}
