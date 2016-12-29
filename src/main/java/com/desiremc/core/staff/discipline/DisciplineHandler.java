package com.desiremc.core.staff.discipline;

import com.desiremc.core.DesireCore;
import com.desiremc.core.sql.AsyncMySQLRunnable;
import com.desiremc.core.sql.Callback;
import com.desiremc.core.sql.MySQLResponse;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.staff.TrackedPlayer;
import com.desiremc.core.util.PlayerConvertUtil;
import com.desiremc.core.util.StringUtil;
import com.desiremc.core.util.logger.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sound.midi.Track;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Timothy Lampen on 11/25/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 *///
public class DisciplineHandler {


    public void getDiscipline(UUID uuid, ReportType type, final Callback<ArrayList<Discipline>> callback){
       getDiscipline(uuid.toString(), type, callback);
    }

    /**
     * @param callback - where the information is put after async get
     */
    public void getDiscipline(String name, final ReportType type, final Callback<ArrayList<Discipline>> callback){
        final long cMillis = System.currentTimeMillis();
        final ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        if (UUID.fromString(name) == null) {
            if(!PlayerConvertUtil.playerExists(name)){
                Logger.error("Tried to get discipline for player " + name + " but cannot find player uuid!");
                return;
            }
            PlayerConvertUtil.getUUID(name, new Callback<UUID>() {
                @Override
                public void onSuccess(UUID done) {
                    getDiscipline(done.toString(), type, callback);
                }
            });
        }
        else{
            DesireCore.getInstance().getSql().asyncExecuteQuery(new AsyncMySQLRunnable() {
                @Override
                public void execute(MySQLResponse response) {
                    ResultSet set = response.getSet();
                    try {
                        while (set.next()) {
                            Discipline d = new Discipline(UUID.fromString(set.getString("reportee")), set.getString("reporter"), type, set.getString("reportType"), set.getString("reason"), set.getLong("reportTime"));
                            disciplines.add(d);
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(disciplines);
                            }
                        }.runTask(DesireCore.getInstance());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    long diff = (System.currentTimeMillis() - cMillis);
                    Logger.debug("It took " + diff + "ms for a response from MySQL");
                    if (diff > 5000) {
                        Logger.error("It took " + diff + "ms for a response from MySQL (async)!");
                    }
                }
            }, "SELECT * FROM dmc_staff_discipline WHERE reportee = ? AND report = ?", name, type.toString());
        }
    }

    public void getTrackedPlayer(final Player player, final Callback<TrackedPlayer> callback){
        getDiscipline(player.getUniqueId(), ReportType.REPORT, new Callback<ArrayList<Discipline>>() {
            @Override
            public void onSuccess(final ArrayList<Discipline> reports) {
                getDiscipline(player.getUniqueId(), ReportType.WARN, new Callback<ArrayList<Discipline>>() {
                    @Override
                    public void onSuccess(final ArrayList<Discipline> warns) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                TrackedPlayer trackedPlayer = new TrackedPlayer();
                                trackedPlayer.setUuid(player.getUniqueId());
                                trackedPlayer.setName(player.getName());
                                trackedPlayer.setReports(reports);
                                trackedPlayer.setWarns(warns);
                                callback.onSuccess(trackedPlayer);
                            }
                        }.runTask(DesireCore.getInstance());
                    }
                });
            }
        });
    }

    public void addDiscipline(UUID uuid, String reporter, ReportType type, String hack, String reason, long reportTime){
        addDiscipline(uuid.toString(), reporter, type, hack, reason, reportTime);
    }

    public void addDiscipline(String name, final String reporter, final ReportType type, final String h, final String reason, final long reportTime) {
        final String hack = h.toUpperCase();
        if (UUID.fromString(name) == null) {
            if(!PlayerConvertUtil.playerExists(name)){
                Logger.error("Tried to add discipline " + type + " from player " + name + " wtith id " + reportTime + " but cannot find player!");
                return;
            }
            PlayerConvertUtil.getUUID(name, new Callback<UUID>() {
                @Override
                public void onSuccess(UUID done) {
                    addDiscipline(done.toString(), reporter, type, h, reason, reportTime);
                }
            });
        } else {
            DesireCore.getInstance().getSql().asyncExecute(new AsyncMySQLRunnable() {
                @Override
                public void execute(MySQLResponse response) {

                }
            }, "INSERT INTO dmc_staff_discipline (report, reportee, reporter, reportType, reason, reportTime) VALUES (?, ?, ?, ?, ?, ?)", type.toString(), name, reporter, hack.toString(), reason, reportTime);
        }
    }

    public void removeDiscipline(UUID uuid, ReportType type, long reportTime){
        removeDiscipline(uuid.toString(), type, reportTime);
    }

    public void removeDiscipline(String name, final ReportType type, final long reportTime){
        if(UUID.fromString(name)==null) {
            if(!PlayerConvertUtil.playerExists(name)){
                Logger.error("Tried to remove discipline " + type + " from player " + name + " wtith id " + reportTime + " but cannot find player!");
                return;
            }
            PlayerConvertUtil.getUUID(name, new Callback<UUID>() {
                @Override
                public void onSuccess(UUID done) {
                    removeDiscipline(done.toString(), type, reportTime);
                }
            });
        }
        else {
            UUID uuid = UUID.fromString(name);
            DesireCore.getInstance().getSql().asyncExecute(new AsyncMySQLRunnable() {
                @Override
                public void execute(MySQLResponse response) {

                }
            }, "DELETE FROM dmc_staff_discipline WHERE reportee = ? AND reportTime = ? AND report = ? ", uuid.toString(), reportTime, type.toString());
        }
    }


    public void getTrackedPlayers(final Callback<Collection<TrackedPlayer>> callback){
        final long cMillis = System.currentTimeMillis();
        DesireCore.getInstance().getSql().asyncExecuteQuery(new AsyncMySQLRunnable() {
            @Override
            public void execute(MySQLResponse response) {
                ResultSet set = response.getSet();
                try {
                    final HashMap<UUID, TrackedPlayer> trackedPlayers = new HashMap<>();
                    while(set.next()){
                        UUID uuid = UUID.fromString(set.getString("reportee"));
                        TrackedPlayer trackedPlayer;
                        if(trackedPlayers.containsKey(uuid)){
                            trackedPlayer = trackedPlayers.get(uuid);
                        }
                        else{
                            trackedPlayer = new TrackedPlayer();
                            trackedPlayer.setName(Bukkit.getOfflinePlayer(uuid).getName());
                            trackedPlayer.setUuid(uuid);
                            trackedPlayers.put(uuid, trackedPlayer);
                        }
                        ReportType type = ReportType.valueOf(set.getString("report"));
                        String reporter = set.getString("reporter");
                        String reportType = set.getString("reportType");
                        String reason = set.getString("reason");
                        long reportTime = set.getLong("reportTime");
                        if(type==ReportType.REPORT){
                            trackedPlayer.addReport(new Discipline(trackedPlayer.getUuid(), trackedPlayer.getName(), reporter, type, reportType, reason ,reportTime));
                        }
                        else{
                            trackedPlayer.addWarn(new Discipline(trackedPlayer.getUuid(), trackedPlayer.getName(), reporter, type, reportType, reason ,reportTime));
                        }
                    }
                    long cTime = System.currentTimeMillis()-cMillis;
                    Logger.debug("It took " + cTime + "ms to get a complete response for all tracked players.");
                    if(cTime>5000){
                        Logger.error("It took more than " + cTime + "ms to get a complete response for all tracked players!");
                    }
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            callback.onSuccess(trackedPlayers.values());
                        }
                    }.runTask(DesireCore.getInstance());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, "SELECT * FROM dmc_staff_discipline");
    }



    public void showStaffDisciplineInv(final Player player, final String targetName, final ReportType type){
        if(UUID.fromString(targetName)==null){
            if(!PlayerConvertUtil.playerExists(targetName)){
                Logger.error("Tried to show discipline inventory of " + targetName + " but cant find uuid");
                return;
            }
            PlayerConvertUtil.getUUID(targetName, new Callback<UUID>() {
                @Override
                public void onSuccess(UUID done) {
                    showStaffDisciplineInv(player, done.toString(), type);
                }
            });
        }
        else {
            UUID uuid = UUID.fromString(targetName);
            String name = UUID.fromString(targetName) == null ? targetName : Bukkit.getOfflinePlayer(uuid).getName();
            final Inventory inv = Bukkit.createInventory(player, 54, ChatColor.GOLD + name + "'s " + (type == ReportType.REPORT ? "Reports" : "Warns"));
            final ArrayList<Discipline> disciplines = new ArrayList<>();
            getDiscipline(uuid, type, new Callback<ArrayList<Discipline>>() {
                @Override
                public void onSuccess(ArrayList<Discipline> done) {
                    disciplines.addAll(done);
                    for (Discipline d : disciplines) {
                        ItemStack is = new ItemStack(StaffModule.getInstance().getStaffConfig().getDisciplineType(d.getHackType()).getItem());
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName("");
                        ArrayList<String> lore = new ArrayList<String>();
                        ;
                        lore.add(ChatColor.YELLOW + "Report ID: " + ChatColor.GRAY + d.getReportTime());
                        lore.add(ChatColor.YELLOW + "Reported For: " + ChatColor.GRAY + d.getHackType());
                        lore.add(ChatColor.YELLOW + "Reporter: " + ChatColor.GRAY + d.getReporter());
                        lore.add(ChatColor.YELLOW + getTimeAgo(System.currentTimeMillis() - d.getReportTime()));
                        lore.add(ChatColor.GOLD + "Reason: " + ChatColor.GRAY + d.getReason());
                        if (type == ReportType.REPORT) {
                            lore.add(ChatColor.ITALIC + "" + ChatColor.RED + "Clicking this item will clear the report.");
                        }
                        im.setLore(lore);
                        is.setItemMeta(im);
                        inv.addItem(is);
                    }
                    player.openInventory(inv);
                }
            });
        }
    }


    private static String getTimeAgo(long l){
        l = l/1000;
        l /= 60;
        l /= 60;
        int i = Math.round(l);
        return i + " hour(s) ago";
    }

    public static  void showDisciplineInv(Player player, Player target, ReportType type){
        Inventory inv = Bukkit.createInventory(player, (int)Math.ceil((double)(StaffModule.getInstance().getStaffConfig().getTotalDisciplines()+1)/9)*9, ChatColor.GOLD + (type==ReportType.REPORT ? "Report" : "Warn") + " Player");

        for(DisciplineType dtype : StaffModule.getInstance().getStaffConfig().getDisciplineTypes()){
            Material m = dtype.getItem();

            ItemStack is = new ItemStack(m);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + dtype.getName());
            is.setItemMeta(im);
            inv.addItem(is);
        }

        ItemStack is = new ItemStack(Material.WOOL);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GRAY + "Target Info");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.YELLOW + "Name: " + target.getName());
        lore.add(ChatColor.YELLOW + "UUID: " + target.getUniqueId());
        lore.add(ChatColor.YELLOW + "Time: " + System.currentTimeMillis());
        im.setLore(lore);
        is.setItemMeta(im);
        inv.addItem(is);

        player.openInventory(inv);
    }

}
