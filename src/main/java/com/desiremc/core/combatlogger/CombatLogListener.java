package com.desiremc.core.combatlogger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Sneling on 25/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
class CombatLogListener implements Listener {

    @EventHandler
    public void onLogout(PlayerQuitEvent e){
        CombatLoggerModule.getInstance().getManager().register(e.getPlayer(), true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        CombatLoggerModule.getInstance().getManager().voidData(e.getPlayer().getUniqueId().toString());
    }

}