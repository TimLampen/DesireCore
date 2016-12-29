package com.desiremc.core.staff.discipline.listeners;

import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.staff.discipline.Discipline;
import com.desiremc.core.staff.discipline.ReportType;
import com.desiremc.core.staff.listeners.TrackedPlayerUpdateEvent;
import com.desiremc.core.staff.listeners.TrackedPlayerUpdateType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/26/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class DisciplineListener implements Listener {
    private HashMap<UUID, Discipline> makingReport = new HashMap<>();

    @EventHandler
    public void onInteractMaking(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if(inv!=null && (inv.getName().equals(ChatColor.GOLD + "Report Player") || inv.getName().equals(ChatColor.GOLD + "Warn Player"))){
            boolean report = inv.getName().equals(ChatColor.GOLD + "Report Player");
            event.setCancelled(true);
            ItemStack is = event.getCurrentItem();
            if(is!=null && !(is.getType()== Material.WOOL)) {
                String type = ChatColor.stripColor(is.getItemMeta().getDisplayName()).toUpperCase().replaceAll(" ", "_");
                ItemStack targetInfo = inv.getItem(inv.firstEmpty()-1);
                UUID targetUUID = UUID.fromString(ChatColor.stripColor(targetInfo.getItemMeta().getLore().get(1).replace("UUID: ", "")));
                long time = Long.parseLong(ChatColor.stripColor(targetInfo.getItemMeta().getLore().get(2).replace("Time: ", "")));
                String targetName = Bukkit.getOfflinePlayer(targetUUID).getName();
                Discipline d =new Discipline(targetUUID, targetName, player.getName(), (report ? ReportType.REPORT : ReportType.WARN), type, null, time);
                makingReport.put(player.getUniqueId(), d);
                player.closeInventory();
                player.sendMessage(Lang.STAFF_LISETNER_INSERT_REASON);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String msg = event.getMessage();
        if(makingReport.containsKey(player.getUniqueId())){
            event.setCancelled(true);
            Discipline d = makingReport.get(player.getUniqueId());
            d.setReason(msg);
            Bukkit.getPluginManager().callEvent(new TrackedPlayerUpdateEvent(player, d.getReporteeName(), d.getReportType()==ReportType.REPORT ? TrackedPlayerUpdateType.REPORT : TrackedPlayerUpdateType.WARN, msg));
            StaffModule.getInstance().getDisciplineHandler().addDiscipline(d.getReportee(), player.getName(), d.getReportType(), d.getHackType(), d.getReason(), d.getReportTime());
            player.sendMessage(Lang.STAFF_COMMAND_TRACK_PLAYER
                    .replaceAll("%TRACK%", d.getReportType()==ReportType.REPORT ? "reported" : "warned")
                    .replaceAll("%PLAYER%", d.getReporteeName())
                    .replaceAll("%REASON%", d.getReason()));
            if(d.getReportType()==ReportType.WARN) {
                if (Bukkit.getPlayer(d.getReportee()) != null) {
                    Player target = Bukkit.getPlayer(d.getReportee());
                    target.sendMessage(Lang.STAFF_COMMAND_WARN_TARGET.replaceAll("%PLAYER%", player.getName()).replaceAll("%REASON%", d.getReason()));
                }
            }
            makingReport.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onInteractStaff(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if(inv!=null && (ChatColor.stripColor(inv.getName()).contains("'s Reports") || ChatColor.stripColor(inv.getName()).contains("'s Warns"))) {
            event.setCancelled(true);
            boolean b = ChatColor.stripColor(inv.getName()).contains("'s Reports");
            if (b) {
                ItemStack is = event.getCurrentItem();
                String targetName = ChatColor.stripColor(inv.getTitle()).split("'s")[0];
                if (is != null && !(is.getType() == Material.AIR)) {
                    long id = Long.parseLong(ChatColor.stripColor(is.getItemMeta().getLore().get(0)).split(" ")[2]);
                    StaffModule.getInstance().getDisciplineHandler().removeDiscipline(targetName, (b ? ReportType.REPORT : ReportType.WARN), id);
                    player.closeInventory();
                    StaffModule.getInstance().getDisciplineHandler().showStaffDisciplineInv(player, targetName, (b ? ReportType.REPORT : ReportType.WARN));
                }
            }
        }
    }
}
