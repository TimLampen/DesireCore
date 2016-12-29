package com.desiremc.core.configs.def;

import com.desiremc.core.util.logger.Logger;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class YMLClassConfig extends YMLConfig {

    public YMLClassConfig(File file) {
        super(file, true);
    }

    @Override
    protected void loadFromFile(){
        try {
            for (Field f : getClass().getDeclaredFields()) {
                if (!getConfig().isSet(toID(f.getName())))
                    getConfig().set(toID(f.getName()), f.get(getClass())); // TODO check for wrappers
                else
                    f.set(getClass(), getConfig().get(toID(f.getName())));
            }
        }catch (IllegalAccessException e){
            Logger.error("An error occured while loading the config.yml: ");
            e.printStackTrace();
        }
    }

    public String toID(String in){
        return in.toLowerCase().replaceAll("_", ".");
    }

}