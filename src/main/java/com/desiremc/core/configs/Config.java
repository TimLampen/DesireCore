package com.desiremc.core.configs;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLClassConfig;

import java.io.File;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 *
 * To add a value to this config, simply add a new variable to this class. Do NOT use final fields.
 */
public class Config extends YMLClassConfig {

    public static boolean DEBUG = true;

    public static String MYSQL_HOST = "localhost";
    public static String MYSQL_DATABASE = "database";
    public static String MYSQL_USERNAME = "username";
    public static String MYSQL_PASSWORD = "password";

    public Config() {
        super(new File(DesireCore.getInstance().getDataFolder() + "/config.yml"));
    }

}