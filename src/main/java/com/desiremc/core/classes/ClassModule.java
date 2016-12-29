package com.desiremc.core.classes;

import com.desiremc.core.DesireCore;
import com.desiremc.core.classes.classes.ArcherClass;
import com.desiremc.core.classes.classes.BardClass;
import com.desiremc.core.classes.classes.HCFClass;
import com.desiremc.core.classes.classes.MinerClass;
import com.desiremc.core.classes.listeners.ArcherListener;
import com.desiremc.core.classes.listeners.ClassUpdateListener;
import com.desiremc.core.classes.listeners.ClassItemUseListener;
import com.desiremc.core.module.Module;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ClassModule extends Module{
    private ArrayList<HCFClass> classes = new ArrayList<HCFClass>();
    private static ClassModule instance;
    private ClassConfig classConfig;

    private ArcherClass archerClass;
    private BardClass bardClass;
    private MinerClass minerClass;

    public ClassModule(){
        instance = this;
        archerClass = new ArcherClass();
        bardClass = new BardClass();
        minerClass = new MinerClass();

        registerClass(archerClass, bardClass, minerClass);

        classConfig = new ClassConfig();
    }

    @Override
    public void onLoad() {
        for(HCFClass hcfClass : classes){
            hcfClass.load();
            hcfClass.classSpecificLoad();
        }
    }

    @Override
    public void onEnable() {
        registerListeners(new ClassItemUseListener(), new ClassUpdateListener(), new ArcherListener());
        new BukkitRunnable() {
            @Override
            public void run() {
                for(HCFClass hcfClass : classes){
                    hcfClass.tick();
                }
            }
        }.runTaskTimer(DesireCore.getInstance(), 0, 20);
    }

    @Override
    public void onDisable() {
        for(HCFClass hcfClass : classes){
            hcfClass.save();
            hcfClass.classSpecificSave();
        }
    }

    public static ClassModule getInstance(){
        return instance;
    }

    public ArrayList<HCFClass> getClasses(){
        return classes;
    }

    public ClassConfig getClassConfig(){
        return classConfig;
    }

    public BardClass getBardClass(){
        return bardClass;
    }

    public MinerClass getMinerClass(){
        return minerClass;
    }

    public ArcherClass getArcherClass(){
        return archerClass;
    }

    public void registerClass(HCFClass... hcfClasses){
        for(HCFClass hcfClass : hcfClasses){
            classes.add(hcfClass);
            Logger.debug("Registered class " + hcfClass.getClassType());
        }
    }
}
