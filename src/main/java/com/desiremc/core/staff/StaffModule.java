package com.desiremc.core.staff;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.module.Module;
import com.desiremc.core.staff.commands.AlertsCommand;
import com.desiremc.core.staff.commands.StaffListCommand;
import com.desiremc.core.staff.commands.chat.ChatCommand;
import com.desiremc.core.staff.commands.gadgets.FreezeServerListener;
import com.desiremc.core.staff.discipline.commands.report.ReportCommand;
import com.desiremc.core.staff.commands.StaffModeCommand;
import com.desiremc.core.staff.discipline.commands.warn.WarnCommand;
import com.desiremc.core.staff.discipline.DisciplineHandler;
import com.desiremc.core.staff.discipline.listeners.DisciplineListener;
import com.desiremc.core.staff.gadgets.FreezeHandler;
import com.desiremc.core.staff.listeners.PlayerChatListener;
import com.desiremc.core.staff.listeners.PlayerMineVeinListener;
import com.desiremc.core.staff.listeners.StaffModeListener;
import com.desiremc.core.staff.listeners.TrackedPlayerUpdateListener;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/16/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class StaffModule extends Module{

    private ArrayList<UUID> alerts = new ArrayList<>();
    private HashMap<UUID, Long> slowChats = new HashMap<UUID, Long>();
    private boolean isGlobalMuted = false;
    private long muteTime;
    private FreezeHandler freezeHandler;
    private DisciplineHandler disciplineHandler;
    private StaffModeHandler staffModeHandler;
    private static StaffModule instance;
    private StaffConfig staffConfig;


    public StaffModule(){
        staffConfig = new StaffConfig();
        instance = this;
        freezeHandler = new FreezeHandler();
        disciplineHandler = new DisciplineHandler();
        staffModeHandler = new StaffModeHandler();
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        DesireCore.registerListeners(new PlayerChatListener(),new TrackedPlayerUpdateListener(), new PlayerMineVeinListener(), new FreezeServerListener(), new DisciplineListener(), new StaffModeListener());
        registerCommand(new WarnCommand());
        registerCommand(new ReportCommand());
        registerCommand(new ChatCommand());
        registerCommand(new AlertsCommand());
        registerCommand(new StaffListCommand());
        registerCommand(new StaffModeCommand());
    }

    @Override
    public void onDisable() {
        staffModeHandler.disable();
        staffConfig.save();
    }

    public FreezeHandler getFreezeHandler(){
        return freezeHandler;
    }

    public ArrayList<UUID> getAlerts(){
        return alerts;
    }

    public void addAlert(Player player){
        player.sendMessage(Lang.STAFF_COMMAND_TOGGLE_ALERT.replaceAll("%ALERT%", "on"));
        alerts.add(player.getUniqueId());
    }

    public void removeAlert(Player player){
        if(alerts.contains(player.getUniqueId())){
            alerts.remove(player.getUniqueId());
            player.sendMessage(Lang.STAFF_COMMAND_TOGGLE_ALERT.replaceAll("%ALERT%", "off"));
        }
    }

    /**
     * @param uuid - uuid of the player you would like to get the expire time of their slow chat cooldown
     */
    public Long getSlowChat(UUID uuid){
        return slowChats.containsKey(uuid) ? slowChats.get(uuid) : 0;
    }

    /**
     * @param time the time when the slow chat cooldown ends
     * @param uuid  the uuid of the player
     */
    public void addSlowChat(UUID uuid, long time){
        slowChats.put(uuid, time);
    }

    /**
     * Returns how long until the global mute is complete
     */
    public long getMuteTime(){
        return muteTime;
    }

    /**
     * @param l the expire time of the mute
     */
    public void setMuteTime(long l){
        muteTime = l;
    }

    //Returns if there is a global mute currently
    public boolean isGlobalMuted(){
        return isGlobalMuted;
    }

    /**
     * @param b if you would like a global mute or not
     */
    public void setGlobalMuted(boolean b){
        isGlobalMuted = b;
    }


    public String trackToString(String reporter, String reason, long time){
        return reporter + "@" + reason + "@" + time;
    }

    //Mutes/unmutes the chat
    public void muteChat(CommandSender sender, boolean mute){
        String s = mute ? "muted" : "unmuted";
        boolean isMuted = isGlobalMuted();
        if(isMuted== mute){
            sender.sendMessage(Lang.STAFF_COMMAND_CANT_MUTE.replaceAll("%TRACK%", s));
        }
        else{
            setGlobalMuted(!isMuted);
            sender.sendMessage(Lang.STAFF_COMMAND_MUTE.replaceAll("%TRACK%", s));
        }
    }


    public static StaffModule getInstance(){
        return instance;
    }

    public DisciplineHandler getDisciplineHandler(){
        return disciplineHandler;
    }

    public StaffModeHandler getStaffModeHandler(){
        return staffModeHandler;
    }

    public StaffConfig getStaffConfig(){
        return staffConfig;
    }

}
