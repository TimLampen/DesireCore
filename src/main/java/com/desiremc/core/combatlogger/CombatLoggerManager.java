package com.desiremc.core.combatlogger;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Sneling on 25/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class CombatLoggerManager {

    private HashMap<String, CombatLogData> current;

    public CombatLoggerManager() {
        current = new HashMap<>();
    }

    public CombatLogData getData(String uuid){
        return current.get(uuid);
    }

    public void register(Player p, boolean spawn){
        if(current.containsKey(p.getUniqueId().toString())){ // TODO: 27/11/2016 Check for spawn variables? as spawn is only true for non-command logout
            CombatLogData data = current.get(p.getUniqueId().toString());

            if(data.isLoggedOut() && data.getExpireIn() > 0)
                return;
        }

        CombatLogData data = new CombatLogData(p, CombatLoggerConfig.TIME * 1000, spawn);

        current.put(p.getUniqueId().toString(), data);
    }

    public CombatLogData voidData(String uuid){
        return current.remove(uuid);
    }

}