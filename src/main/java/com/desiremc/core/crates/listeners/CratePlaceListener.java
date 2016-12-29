package com.desiremc.core.crates.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.crates.KeyModule;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class CratePlaceListener implements Listener {

    @EventHandler
    public void onCratePlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack is = event.getItemInHand();
        if(PermChecker.has(player, Perm.KEY_CHEST_GENERATE)) {
            if (KeyModule.getInstance().isKeyChest(is)) {
                KeyModule.getInstance().addKeyChest(block.getLocation());
                player.sendMessage(Lang.LISTENER_CRATE_PLACE);
            }
        }
        else{
            player.sendMessage(Lang.LISTENER_KEY_NO_PERM);
        }
    }
}
