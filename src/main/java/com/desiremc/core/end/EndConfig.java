package com.desiremc.core.end;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class EndConfig extends YMLConfig {

    Location exit, enter;

    public EndConfig() {
        super(new File(DesireCore.getInstance().getDataFolder(), "end.yml"), true);
        int x = getConfig().getInt("exit.x");
        int y = getConfig().getInt("exit.y");
        int z = getConfig().getInt("exit.z");
        String world = getConfig().getString("exit.world");

        exit = new Location(Bukkit.getWorld(world), x, y, z);

        x = getConfig().getInt("enter.x");
        y = getConfig().getInt("enter.y");
        z = getConfig().getInt("enter.z");
        world = getConfig().getString("enter.world");

        enter = new Location(Bukkit.getWorld(world), x, y, z);
    }

    public Location getExit(){
        return exit;
    }

    public Location getEnter(){
        return enter;
    }
}
