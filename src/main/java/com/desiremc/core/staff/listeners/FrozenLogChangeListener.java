package com.desiremc.core.staff.listeners;

import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.staff.discipline.ReportType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Timothy Lampen on 11/24/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class FrozenLogChangeListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(StaffModule.getInstance().getFreezeHandler().isFrozen(player.getUniqueId())){
            StaffModule.getInstance().getFreezeHandler().addLoggedOut(player.getUniqueId());
        }
    }//

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(StaffModule.getInstance().getFreezeHandler().isLoggedOut(player.getUniqueId())){
            //rather have them playing then waiting for staff to unfreeze them
            player.sendMessage(Lang.STAFF_LISTENER_PLAYER_UNFROZEN.replace("%PLAYER%", "Server"));
            StaffModule.getInstance().getFreezeHandler().removeFrozen(player);
            StaffModule.getInstance().getDisciplineHandler().addDiscipline(player.getUniqueId(), player.getName(), ReportType.REPORT, "other", "Logged in from an outstanding freeze unresolved from staff.", System.currentTimeMillis());
            StaffModule.getInstance().getFreezeHandler().removeLoggedOut(player.getUniqueId());
        }
    }
}
