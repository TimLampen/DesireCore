package com.desiremc.core.combatlogger;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Created by Sneling on 25/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class CombatLogData {

    private Villager entity = null;
    private long expire;

    public CombatLogData(Player p, long expire, boolean spawn) {
        this.expire = System.currentTimeMillis() + expire;

        if(spawn){
            entity = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);

            entity.setCustomNameVisible(true);
            entity.setCustomName(p.getName());
        }
    }

    public Villager getEntity() {
        return entity;
    }

    public long getExpireIn() {
        return System.currentTimeMillis() - expire;
    }

    public boolean isLoggedOut(){
        return entity == null;
    }

}