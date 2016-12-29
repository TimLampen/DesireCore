package com.desiremc.core.shops.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.shops.ShopModule;
import com.desiremc.core.util.StringUtil;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ShopInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getClickedBlock()!=null) {
            Block b = event.getClickedBlock();
            if (b.getType().toString().contains("SIGN")) {
                Sign sign = (Sign) b.getState();
                if (ShopModule.getInstance().isShopSign(sign)) {
                    int amount = Integer.parseInt(sign.getLine(1));
                    ItemStack is = ShopModule.getInstance().stringToItemStack(sign.getLine(2));
                    is.setAmount(amount);
                    int price = Integer.parseInt(sign.getLine(3).replace("$", ""));

                    if (sign.getLine(0).equals(ChatColor.GREEN + "[Sell]")) {
                        int amtInInv = 0;
                        for (ItemStack content : player.getInventory().getContents()) {
                            if (content!=null && content.isSimilar(is)) {
                                amtInInv += content.getAmount();
                            }
                        }
                        if (amtInInv < amount) {
                            player.sendMessage(Lang.SHOP_SIGN_NOT_ENOUGH_ITEMS);
                            return;
                        }
                        player.getInventory().removeItem(is);
                        player.updateInventory();
                        DesireCore.getInstance().getEco().depositPlayer(player, price);
                        player.sendMessage(Lang.SHOP_SELL_ITEMS.replaceAll("%AMOUNT%", amount + "").replaceAll("%TYPE%", StringUtil.capitalizeFirst(is.getType().toString().toLowerCase().replace("_", " "))));
                    }
                    else if(sign.getLine(0).equals(ChatColor.GREEN + "[Buy]")){
                        if(DesireCore.getInstance().getEco().has(player, price)){
                            if(!(player.getInventory().firstEmpty()==-1)){
                                player.getInventory().addItem(is);
                                player.updateInventory();
                                player.sendMessage(Lang.SHOP_BUY_ITEMS.replaceAll("%AMOUNT%", amount + "").replaceAll("%TYPE%", StringUtil.capitalizeFirst(is.getType().toString().toLowerCase().replace("_", " "))));
                            }
                            else{
                                player.sendMessage(Lang.INVENTORY_FULL);
                            }
                        }
                        else{
                            player.sendMessage(Lang.ECO_NO_MONEY);
                        }
                    }
                }
            }
        }
    }
}
