package com.desiremc.core.staff.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/18/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(PermChecker.has(player, Perm.STAFF_OWNER) || PermChecker.has(player, Perm.STAFF_ADMIN) || PermChecker.has(player, Perm.STAFF_MOD) || PermChecker.has(player, Perm.STAFF_HELPER)){
            return;
        }
        else if(StaffModule.getInstance().isGlobalMuted()){
            player.sendMessage(Lang.STAFF_LISTENER_STILL_MUTE.replaceAll("%TIME%", "infinite"));
            event.setCancelled(true);
            Logger.debug("Player tried to talk while there is a global mute.");
            return;
        }//handles server wide mutes
        else if(StaffModule.getInstance().getMuteTime()<System.currentTimeMillis()){
            if(StaffModule.getInstance().getSlowChat(player.getUniqueId())>System.currentTimeMillis()){
                event.setCancelled(true);
                DecimalFormat df = new DecimalFormat("#.#");
                double time = StaffModule.getInstance().getSlowChat(player.getUniqueId())/1000;
                time -=System.currentTimeMillis()/1000;
                player.sendMessage(Lang.STAFF_LISTENER_STILL_MUTE.replace("%TIME%", df.format(time)));
            }
            else{
                StaffModule.getInstance().addSlowChat(player.getUniqueId(), 15*1000+System.currentTimeMillis());
            }
        }

        String msg = event.getMessage();
        for(UUID uuid : StaffModule.getInstance().getAlerts()){
            if(Bukkit.getPlayer(uuid)!=null){
                Player staff = Bukkit.getPlayer(uuid);
                if(msg.toLowerCase().contains("@" + staff.getName().toLowerCase())){
                    msg.replace("@" + staff.getName(), ChatColor.GREEN + "@" + staff.getName() + ChatColor.RESET);
                    staff.getWorld().playSound(staff.getLocation(), Sound.LEVEL_UP, 10, 1);
                }
            }
        }
    }
}
