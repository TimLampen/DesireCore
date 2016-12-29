package com.desiremc.core.staff.listeners;

import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/19/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class TrackedPlayerUpdateListener implements Listener {

    @EventHandler
    public void onUpdate(TrackedPlayerUpdateEvent event){
        Player player = event.getPlayer();
        String targetName = event.getTarget();
        Logger.debug("Updated track player for " + player.getName() + " found");
        String argument = event.getArgument();
        TrackedPlayerUpdateType track = event.getUpdate();
        for(UUID uuid : StaffModule.getInstance().getAlerts()){
            if(Bukkit.getPlayer(uuid)!=null) {
                Player staff = Bukkit.getPlayer(uuid);
                if (track==TrackedPlayerUpdateType.REPORT) {
                    staff.sendMessage(Lang.STAFF_LISTENER_NEW_REPORT.replaceAll("%REPORTER%", player.getName()).replaceAll("%REPORTEE%", targetName).replaceAll("%TRACK%", "reported").replaceAll("%REASON%", argument));
                }
                else if(track==TrackedPlayerUpdateType.WARN){
                    staff.sendMessage(Lang.STAFF_LISTENER_NEW_REPORT.replaceAll("%REPORTER%", player.getName()).replaceAll("%REPORTEE%", targetName).replaceAll("%TRACK%", "warned").replaceAll("%REASON%", argument));
                }
                else if(track==TrackedPlayerUpdateType.NAME){
                    staff.sendMessage(Lang.STAFF_LISTENER_NEW_NAME.replaceAll("%PLAYER%", player.getName()).replaceAll("%NAME%", argument));
                }
            }
        }
    }
}
