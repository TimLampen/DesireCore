package com.desiremc.core.util;

import com.desiremc.core.DesireCore;
import com.desiremc.core.sql.AsyncMySQLRunnable;
import com.desiremc.core.sql.Callback;
import com.desiremc.core.sql.MySQLResponse;
import com.desiremc.core.staff.listeners.TrackedPlayerUpdateEvent;
import com.desiremc.core.staff.listeners.TrackedPlayerUpdateType;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class PlayerConvertUtil implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player= event.getPlayer();
        updatePlayer(player.getName(), player.getUniqueId(), new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean done) {
                if(done){
                    Bukkit.getPluginManager().callEvent(new TrackedPlayerUpdateEvent(player, player.getName(), TrackedPlayerUpdateType.NAME, player.getName()));
                }
            }
        });
    }

    /**
     * @param callback where the data is going to be put after the method, returns null if there is no uuid.
     * @param name the name of the player you want to find
     */
    public static void getUUID(final String name, final Callback<UUID> callback) {
        DesireCore.getInstance().getSql().asyncExecuteQuery(new AsyncMySQLRunnable() {
            @Override
            public void execute(MySQLResponse response) {
                ResultSet set = response.getSet();
                try {
                    if(set.getFetchSize()==0){
                        callback.onSuccess(null);
                        return;
                    }
                    if(set.getFetchSize()>1){
                        Logger.error("Call to MySQL for player name " + name + " had two results, only taking first one.");
                    }
                    while(set.next()){
                        final UUID uuid = UUID.fromString(set.getString("uuid"));
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                callback.onSuccess(uuid);
                            }
                        };
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, "SELECT * FROM dmc_playerdata WHERE name = ?", name);
    }

    public static void updatePlayer(final String name, final UUID uuid, final Callback<Boolean> callback){
        MySQLResponse response = DesireCore.getInstance().getSql().executeQuery("SELECT * FROM dmc_playerdata WHERE uuid = ?", uuid);
        try {
            ResultSet set = response.getSet();
            if(!set.next()){
                DesireCore.getInstance().getSql().asyncExecute(new AsyncMySQLRunnable() {
                    @Override
                    public void execute(MySQLResponse response) {
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                callback.onSuccess(true);
                            }
                        };
                    }
                }, "INSERT INTO dmc_playerdata (name, uuid) VALUES (?, ?)", name, uuid);
            }
            else {
                if (set.getString("name").equals(name)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(false);
                        }
                    };
                } else {
                    DesireCore.getInstance().getSql().asyncExecuteUpdate(new AsyncMySQLRunnable() {
                        @Override
                        public void execute(MySQLResponse response) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(false);
                                }
                            };
                        }
                    }, "UPDATE dmc_deathban_accounts SET name=? WHERE uuid=?", name, uuid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean playerExists(UUID uuid){
        MySQLResponse response = DesireCore.getInstance().getSql().executeQuery("SELECT * FROM dmc_playerdata WHERE uuid = ?", uuid);
        ResultSet set = response.getSet();
        try {
            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean playerExists(String name){
        MySQLResponse response = DesireCore.getInstance().getSql().executeQuery("SELECT * FROM dmc_playerdata WHERE name = ?", name);
        ResultSet set = response.getSet();
        try {
            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
