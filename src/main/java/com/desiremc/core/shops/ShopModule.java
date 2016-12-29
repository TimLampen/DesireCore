package com.desiremc.core.shops;

import com.desiremc.core.DesireCore;
import com.desiremc.core.module.Module;
import com.desiremc.core.shops.listeners.ShopCreateListener;
import com.desiremc.core.shops.listeners.ShopInteractListener;
import com.desiremc.core.util.logger.Logger;
import com.desiremc.core.util.numbers.IntegerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ShopModule extends Module {

    private static ShopModule instance;

    public ShopModule(){
       instance = this;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        DesireCore.registerListeners(new ShopCreateListener(), new ShopInteractListener());
    }

    @Override
    public void onDisable() {

    }

    public boolean isShopSign(Sign s){
        return s.getLine(0).equals(ChatColor.GREEN + "[Sell]") || s.getLine(0).equals(ChatColor.GREEN + "[Buy]");
    }

    public ItemStack stringToItemStack(String s){
        if(!s.contains(":")) {
            String temp = s.substring(0, 1);
            for (char c : s.substring(1).toCharArray()) {
                if (Character.isUpperCase(c)) {
                    temp += "_" + c;
                } else {
                    temp += c;
                }
            }
            Material mat = Material.getMaterial(temp.toUpperCase());
            if(mat==null){
                Logger.error("Unable to convert/find string to material (" + s + " -> " + temp.toUpperCase() + ")");
                return null;
            }
            return new ItemStack(mat);
        }
        else{
            String[] split = s.split(":");
            if(IntegerUtil.isInteger(split[0]) && IntegerUtil.isInteger(split[1])){
                Integer id = Integer.parseInt(split[0]);
                short data = (short)Integer.parseInt(split[1]);
                Material mat = Material.getMaterial(id);
                if(mat==null){
                    Logger.error("Unable to convert/find int to material (" + s + " -> " + id+ ")");
                    return null;
                }
                return new ItemStack(mat, 1, data);
            }
        }
        return null;
    }

    public static ShopModule getInstance(){
        return instance;
    }
}
