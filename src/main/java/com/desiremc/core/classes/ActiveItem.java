package com.desiremc.core.classes;

import org.bukkit.potion.PotionEffect;

/**
 * Created by Timothy Lampen on 12/10/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ActiveItem {
    PotionEffect effect;
    boolean enemies;
    int arg;

    public ActiveItem(PotionEffect effect, boolean enemies, int arg){
        this.enemies = enemies;
        this.arg = arg;
        this.effect = effect;
    }

    public PotionEffect getEffect() {
        return effect;
    }

    public boolean isEnemies() {
        return enemies;
    }

    //for now Arg is energy for bard class, cooldown for item for archer class
    public int getArg() {
        return arg;
    }
}
