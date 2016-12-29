package com.desiremc.core.configs.def;

import com.desiremc.core.DesireCore;
import com.desiremc.core.util.logger.LColor;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 *
 * You should make one class per config. Simply make your class extend this class.
 */
public class YMLConfig {

    private final File file;
    private FileConfiguration config;

    private final boolean loadFromFile;

    public YMLConfig(File file, boolean loadFromFile) {
        this.file = file;
        this.loadFromFile = loadFromFile;

        setup();
    }

    private void setup(){
        if(!DesireCore.getInstance().getDataFolder().exists())
            DesireCore.getInstance().getDataFolder().mkdirs();

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Logger.debug(LColor.GREEN + "Created config file " + file.getName());
        }

        load();
        save();

        if(loadFromFile) {
            loadFromFile();
            save();

            Logger.debug(LColor.GREEN + "Loaded " + file.getName() + " with its default values if not set.");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    protected void loadFromFile(){
        FileConfiguration temporary = YamlConfiguration.loadConfiguration(DesireCore.getInstance().getResource(file.getName()));

        for(String key: temporary.getKeys(true)){
            if(!config.isSet(key)){
                Object obj = temporary.get(key);

                if(obj instanceof MemorySection)
                    continue;

                config.set(key, obj);
                Logger.debug("[" + file.getName() + "] Set Default Value for '" + key + "' to '" + obj + "'");
            }
        }
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            Logger.error("An error occured while saving config: " + file.getName());
            e.printStackTrace();
        }
    }

    private void load(){
        config = YamlConfiguration.loadConfiguration(file);
        postLoad();
    }

    /**
     * Override me! :)
     */
    public void postLoad(){}

}