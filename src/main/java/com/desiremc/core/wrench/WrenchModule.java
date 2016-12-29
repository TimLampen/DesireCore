package com.desiremc.core.wrench;

import com.desiremc.core.DesireCore;
import com.desiremc.core.module.Module;
import com.desiremc.core.wrench.commands.WrenchCommand;
import com.desiremc.core.wrench.listeners.WrenchBreakBlockListener;
import com.desiremc.core.wrench.listeners.WrenchSpawnerPlaceListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WrenchModule extends Module{


    private static WrenchModule instance;
    private WrenchConfig wrenchConfig;

    public WrenchModule(){
        instance = this;
    }
    @Override
    public void onLoad() {
        wrenchConfig = new WrenchConfig();
    }

    @Override
    public void onEnable() {
        DesireCore.registerListeners(new WrenchBreakBlockListener(), new WrenchSpawnerPlaceListener());
        registerCommand(new WrenchCommand());
    }

    @Override
    public void onDisable() {
        wrenchConfig.save();
    }

    /**
     * Returns a wrench
     */
    public ItemStack generateWrench(){
        ItemStack is = new ItemStack(Material.GOLD_HOE);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(wrenchConfig.getWrenchName());
        im.setLore(wrenchConfig.getLore());
        is.setItemMeta(im);
        return is;
    }

    /**
     * @param is the item you want to see if it is a wrench or not
     * Returns true if the item is a wrench
     */
    public boolean isWrench(ItemStack is){
        return is!=null && is.getType()==Material.GOLD_HOE && is.hasItemMeta() && is.getItemMeta().getDisplayName().equals(wrenchConfig.getWrenchName());
    }

    public static WrenchModule getInstance(){
        return instance;
    }

    public WrenchConfig getWrenchConfig(){
        return wrenchConfig;
    }

}
