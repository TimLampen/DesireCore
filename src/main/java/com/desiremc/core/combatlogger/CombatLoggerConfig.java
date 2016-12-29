package com.desiremc.core.combatlogger;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLClassConfig;

import java.io.File;

/**
 * Created by Sneling on 25/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class CombatLoggerConfig extends YMLClassConfig {

//    Time in seconds
    public static long TIME = 30;

//    Distance in meters (blocks)
    public static double DISTANCE = 20;

    public CombatLoggerConfig() {
        super(new File(DesireCore.getInstance().getDataFolder() + "/combatlog/config.yml"));
    }

}