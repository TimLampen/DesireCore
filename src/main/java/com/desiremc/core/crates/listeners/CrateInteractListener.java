package com.desiremc.core.crates.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.crates.KeyModule;
import com.desiremc.core.crates.TierInfo;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.util.StringUtil;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class CrateInteractListener implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block chest = event.getClickedBlock();
        if(chest!=null){
            if(KeyModule.getInstance().isKeyChest(chest)) {
                event.setCancelled(true);
                if (KeyModule.getInstance().isValidKey(player.getItemInHand())) {
                    ItemStack hand = player.getItemInHand();
                    TierInfo info = KeyModule.getInstance().getTierInfo(hand);
                     if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (info == null) {
                            Logger.error("Unable to find TierInfo for ItemStack key, but able to find String name of key with name: " + hand.getItemMeta().getDisplayName());
                            player.sendMessage(Lang.LISTENER_INTERACT_CRATE_NO_KEY);
                            return;
                        }
                        player.getInventory().remove(hand);
                        if (hand.getAmount() > 1) {
                            hand.setAmount(hand.getAmount() - 1);
                            player.getInventory().addItem(hand);
                        }

                        KeyModule.getInstance().getKeyInventory().generateCrateInventory(player, info);
                    }
                    else {
                         DecimalFormat df= new DecimalFormat("#.##");
                         Inventory inv = Bukkit.createInventory(player, (int) Math.ceil((double)info.getRewards().size() / (double)9) * 9, Lang.REWARD_PREVIEW_TITLE.replace("%KEY%", StringUtil.shortenString(StringUtil.capitalizeFirst(ChatColor.stripColor(info.getDisplayName().split(" ")[0].toLowerCase())), 32, false)));
                         for(ItemStack is : info.getRewards().keySet()){
                             int weight = info.getRewards().get(is);
                             ItemStack clone = is.clone();
                             ItemMeta im = clone.getItemMeta();
                             im.setDisplayName(KeyModule.getInstance().getKeyConfig().getRewardName().replace("%CHANCE%", df.format((double)weight/(double)info.getTotalWeight()*100)));
                             clone.setItemMeta(im);
                             inv.addItem(clone);
                         }
                         player.openInventory(inv);
                     }
                }
                else if(event.getAction()==Action.LEFT_CLICK_BLOCK && PermChecker.has(player, Perm.GLOBAL_ALL) && player.getGameMode()== GameMode.CREATIVE){
                    event.setCancelled(false);
                }
                else{
                    player.sendMessage(Lang.LISTENER_INTERACT_CRATE_NO_KEY);
                    player.setVelocity(player.getLocation().getDirection().setY(0).multiply(-2));
                }
            }
        }
    }
}
