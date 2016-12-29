package com.desiremc.core.wrench;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLConfig;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy Lampen on 12/4/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WrenchConfig extends YMLConfig {

    private String wrenchName;
    private List<String> wrenchLore;

    public WrenchConfig() {
        super(new File(DesireCore.getInstance().getDataFolder(), "wrench.yml"), true);
        ArrayList<String> temp = new ArrayList<String>();
        for(String s : getConfig().getStringList("wrench.lore")){
            temp.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        wrenchLore = temp;
        wrenchName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("wrench.name"));
    }

    public String getWrenchName() {
        return wrenchName;
    }

    public List<String> getLore() {
        return wrenchLore;
    }
}
