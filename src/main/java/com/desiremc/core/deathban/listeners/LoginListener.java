package com.desiremc.core.deathban.listeners;

import com.desiremc.core.deathban.DeathBanAccount;
import com.desiremc.core.deathban.DeathBanModule;
import com.desiremc.core.lang.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Created by Sneling on 18/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e){
        DeathBanAccount account = DeathBanModule.getInstance().getAccountManager().getAccount(e.getUniqueId().toString());

        if(account.isCurrentlyBanned()){
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(Lang.DEATHBAN_LOGIN_BANNED.replaceAll("%TIME%", account.getFormattedTime()));
        }
    }

}