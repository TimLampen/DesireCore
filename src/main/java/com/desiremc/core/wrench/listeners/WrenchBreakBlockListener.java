package com.desiremc.core.wrench.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.util.StringUtil;
import com.desiremc.core.wrench.WrenchModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WrenchBreakBlockListener implements Listener{

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block b = event.getBlock();
        ItemStack is = player.getItemInHand();

        if(WrenchModule.getInstance().isWrench(is)){
            if(b.getType()== Material.ENDER_PORTAL_FRAME) {
                //each wrench has 6 uses, golden hoe has 33 durability... math
                is.setDurability((short) (is.getDurability() + 6));
                ItemStack frame = new ItemStack(Material.ENDER_PORTAL_FRAME);
                player.getWorld().dropItem(b.getLocation(), frame);
            }
            else if(b.getType()==Material.MOB_SPAWNER){
                is.setDurability((short) (is.getDurability() + 6));
                CreatureSpawner spawner = (CreatureSpawner)b.getState();
                ItemStack cage = new ItemStack(Material.MOB_SPAWNER);
                ItemMeta im = cage.getItemMeta();
                im.setDisplayName(ChatColor.RESET + StringUtil.capitalizeFirst(spawner.getSpawnedType().toString().toLowerCase()) + " Spawner");
                cage.setItemMeta(im);
                player.getWorld().dropItem(b.getLocation(), cage);
            }
            else{
                event.setCancelled(true);
                player.sendMessage(Lang.WRENCH_BREAK_BAD_BLOCK);
            }
        }
    }
}
