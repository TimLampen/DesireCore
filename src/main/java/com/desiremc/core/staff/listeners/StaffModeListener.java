package com.desiremc.core.staff.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.sql.Callback;
import com.desiremc.core.staff.ExamineGUI;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.staff.TrackedPlayer;
import com.desiremc.core.staff.discipline.Discipline;
import com.desiremc.core.staff.discipline.ReportType;
import com.desiremc.core.staff.discipline.TrackedPlayerComparator;
import com.desiremc.core.staff.gadgets.GadgetType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Created by Timothy Lampen on 11/21/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class StaffModeListener implements Listener {
    private ArrayList<UUID> invisible = new ArrayList<UUID>();
    private HashMap<UUID, BukkitTask> cpsTask = new HashMap<>();
    private Random ran = new Random();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onToggleFlight(PlayerToggleFlightEvent event){
        Player player = event.getPlayer();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null){
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(PermChecker.has(player, Perm.STAFF_ALERTS) && !StaffModule.getInstance().getAlerts().contains(player.getUniqueId())){
            StaffModule.getInstance().addAlert(player);
        }
        for(UUID uuid : invisible){
            if(Bukkit.getPlayer(uuid)!=null){
                Player staff = Bukkit.getPlayer(uuid);
                player.hidePlayer(staff);
            }
            else{
                invisible.remove(uuid);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(invisible.contains(player.getUniqueId())){
            for(Player playerz : Bukkit.getOnlinePlayers()){
                playerz.showPlayer(player);
            }
            invisible.remove(player.getUniqueId());
        }
        for(UUID uuid : invisible){
            if(Bukkit.getPlayer(uuid)!=null){
                Player staff = Bukkit.getPlayer(uuid);
                player.showPlayer(staff);
            }
            else{
                invisible.remove(uuid);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null && event.getItem()!=null){
            ItemStack is = event.getItem();
            if(is.hasItemMeta() && is.getItemMeta().getDisplayName()!=null){
                ItemMeta im = is.getItemMeta();
                String name = im.getDisplayName();
                GadgetType type = StaffModule.getInstance().getStaffModeHandler().getGadgetType(name);
                event.setCancelled(true);
                if(type==GadgetType.TELE_CURSOR){
                    Block b  = player.getTargetBlock((Set<Material>)null, 100);
                    if(b==null){
                        player.sendMessage(Lang.STAFF_LISTENER_CURSOR_OUT_OF_RANGE);
                    }
                    else{
                        player.teleport(b.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
                else if(type==GadgetType.TELE_RANDOM_PLAYER){
                    int index = ran.nextInt(Bukkit.getOnlinePlayers().size());
                    Player target = (Player)Bukkit.getOnlinePlayers().toArray()[index];
                    player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    player.sendMessage(Lang.STAFF_LISTENER_RANDOM_TELE.replace("%TARGET%", target.getName()));
                }
                else if(type==GadgetType.VISABILITY){
                    if(invisible.contains(player.getUniqueId())){
                        for(Player playerz : Bukkit.getOnlinePlayers()){
                            playerz.showPlayer(player);
                        }
                        invisible.remove(player.getUniqueId());
                        player.sendMessage(Lang.STAFF_MODE_INVISIBILITY_TOGGLE_OFF);
                    }
                    else{
                        invisible.add(player.getUniqueId());
                        for(Player playerz : Bukkit.getOnlinePlayers()){
                            playerz.hidePlayer(player);
                        }
                        player.sendMessage(Lang.STAFF_MODE_INVISIBILITY_TOGGLE_ON);
                    }
                }
                else if(type==GadgetType.REPORTS){
                    StaffModule.getInstance().getDisciplineHandler().getTrackedPlayers(new Callback<Collection<TrackedPlayer>>() {
                        @Override
                        public void onSuccess(Collection<TrackedPlayer> done) {
                            Inventory inv = Bukkit.createInventory(player, 54, ChatColor.RED + "Reports");
                            ArrayList<TrackedPlayer> players = new ArrayList<TrackedPlayer>();
                            players.addAll(done);

                            Collections.sort(players, new TrackedPlayerComparator());
                            int amt = 0;
                            for(TrackedPlayer tracked : players){
                                if(amt>=54 || tracked.getReports().size()==0){
                                    break;
                                }
                                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                                SkullMeta meta = (SkullMeta)head.getItemMeta();
                                meta.setOwner(tracked.getName());
                                meta.setDisplayName(ChatColor.GOLD + tracked.getName());
                                ArrayList<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.YELLOW +  "Reports: " + ChatColor.GRAY + tracked.getReports().size());
                                lore.add(ChatColor.DARK_GRAY + tracked.getUuid().toString());
                                meta.setLore(lore);
                                head.setItemMeta(meta);
                                inv.addItem(head);
                                amt++;
                            }
                            player.openInventory(inv);
                        }
                    });
                }
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null && event.getClickedInventory()!=null && (event.getClickedInventory().getName().equals(ChatColor.RED  +"Reports") || ChatColor.stripColor(event.getClickedInventory().getName()).contains("'s Inventory"))){
            ItemStack is = event.getCurrentItem();
            event.setCancelled(true);
            if(is!=null && is.getType()==Material.SKULL_ITEM){
                UUID targetUUID = UUID.fromString(ChatColor.stripColor(is.getItemMeta().getLore().get(1)));
                player.closeInventory();
                StaffModule.getInstance().getDisciplineHandler().showStaffDisciplineInv(player, targetUUID.toString(), ReportType.REPORT);
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event){
        final Player player = event.getPlayer();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null && event.getRightClicked() instanceof Player && player.getItemInHand()!=null && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName()) {
            String name = player.getItemInHand().getItemMeta().getDisplayName();
            GadgetType type = StaffModule.getInstance().getStaffModeHandler().getGadgetType(name);
            final Player target = (Player) event.getRightClicked();
            if (type==GadgetType.FREEZE) {
                if (StaffModule.getInstance().getFreezeHandler().isFrozen(target.getUniqueId())) {
                    StaffModule.getInstance().getFreezeHandler().removeFrozen(target);
                    target.sendMessage(Lang.STAFF_LISTENER_PLAYER_UNFROZEN.replace("%PLAYER%", player.getName()));
                    player.sendMessage(Lang.STAFF_LISTENER_TARGET_UNFROZEN.replace("%PLAYER%", target.getName()));
                } else {
                    target.sendMessage(Lang.STAFF_LISTENER_PLAYER_FROZEN.replace("%PLAYER%", player.getName()));
                    player.sendMessage(Lang.STAFF_LISTENER_TARGET_FROZEN.replace("%PLAYER%", target.getName()));
                    StaffModule.getInstance().getFreezeHandler().addFrozen(player, target);
                }
            }
            else if(type==GadgetType.MOUNT){
                target.setPassenger(player);
                player.sendMessage(Lang.STAFF_LISTENER_MOUNT);
            }
            else if(type==GadgetType.CPS){
                if(cpsTask.containsKey(player.getUniqueId())){
                    cpsTask.get(player.getUniqueId()).cancel();
                    StaffModule.getInstance().getStaffModeHandler().removeCPSTracker(target.getUniqueId());
                }
                else{
                    StaffModule.getInstance().getStaffModeHandler().addCPSTracker(target.getUniqueId());
                    cpsTask.put(player.getUniqueId(), new BukkitRunnable(){
                        @Override
                        public void run(){
                            player.sendMessage(Lang.STAFF_MODE_CPS.replace("%CLICKS%", StaffModule.getInstance().getStaffModeHandler().getCPS(target.getUniqueId()) + ""));
                            StaffModule.getInstance().getStaffModeHandler().addCPS(target.getUniqueId());
                        }
                    }.runTaskTimer(DesireCore.getInstance(), 0, 20));
                }
            }
            else if(type==GadgetType.TARGET_INVENTORY){
                ExamineGUI.createInv(player, target, new Callback<Inventory>() {
                    @Override
                    public void onSuccess(Inventory done) {
                        player.openInventory(done);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null){
            event.setCancelled(true);
            player.updateInventory();
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId())!=null) {
            String name = event.getItemInHand().getItemMeta().getDisplayName();
            GadgetType type = StaffModule.getInstance().getStaffModeHandler().getGadgetType(name);
            if (type != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCPS(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(StaffModule.getInstance().getStaffModeHandler().hasCPSTracker(player.getUniqueId())){
            if(event.getAction().toString().contains("LEFT")){
                StaffModule.getInstance().getStaffModeHandler().addCPS(player.getUniqueId());
            }
        }
    }
}
