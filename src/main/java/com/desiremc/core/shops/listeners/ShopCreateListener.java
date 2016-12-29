package com.desiremc.core.shops.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.shops.ShopModule;
import com.desiremc.core.util.logger.Logger;
import com.desiremc.core.util.numbers.IntegerUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ShopCreateListener implements Listener {

    @EventHandler
    public void onCreate(SignChangeEvent event){
        Player player = event.getPlayer();
        Sign sign = (Sign)event.getBlock().getState();
     //   Logger.debug(event.getLine(1));
        if(event.getLine(0).equals("[Sell]") || event.getLine(0).equals("[Buy]")){
            if(event.getLine(1).equals("") || event.getLine(2).equals("") || event.getLine(3).equals("")){
                sign.getBlock().breakNaturally();
                player.sendMessage(Lang.SHOP_SIGN_NO_INFO);
                return;
            }
            if(PermChecker.has(player, Perm.SHOP_CREATE)){
                sign.setLine(0, ChatColor.GREEN + event.getLine(0));
                if(ShopModule.getInstance().stringToItemStack(event.getLine(2))==null){
                    player.sendMessage(Lang.SHOP_ITEM_NULL.replaceAll("%STRING%", event.getLine(2)));
                    sign.getBlock().breakNaturally();
                    return;
                }
                if(!IntegerUtil.isInteger(event.getLine(3).replace("$", "")) || !IntegerUtil.isInteger(event.getLine(1))){
                    player.sendMessage(Lang.SHOP_PRICE_NULL.replaceAll("%STRING%", event.getLine(3)));
                    sign.getBlock().breakNaturally();
                    return;
                }
                event.setLine(0, ChatColor.GREEN + event.getLine(0));
                player.sendMessage(Lang.SHOP_SIGN_CREATE);
            }
            else{
                sign.getBlock().breakNaturally();
                player.sendMessage(Lang.SHOP_SIGN_NO_PERM);
            }
        }
    }
}
