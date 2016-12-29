package com.desiremc.core.deathban.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.deathban.DeathBanAccount;
import com.desiremc.core.deathban.DeathBanConfig;
import com.desiremc.core.deathban.DeathBanModule;
import com.desiremc.core.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

/**
 * Created by Sneling on 18/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(final PlayerDeathEvent e){
        e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());

        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                DeathBanAccount account = DeathBanModule.getInstance().getAccountManager().getAccount(e.getEntity().getUniqueId().toString());

                account.setLives(account.getLives() - 1);
                if(account.getLives() == 0){
                    account.setBanExpireIn(DeathBanConfig.BAN_TIME);
                    e.getEntity().kickPlayer(Lang.DEATHBAN_LOGIN_BANNED.replaceAll("%TIME%", account.getFormattedTime()));

                    try {
                        account.updateRemoteData();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

}