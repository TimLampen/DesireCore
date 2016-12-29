package com.desiremc.core.staff;

import com.desiremc.core.staff.gadgets.GadgetType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/26/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class StaffModeHandler {
    private ArrayList<ItemStack> staffModeItems = new ArrayList<>();
    private HashMap<UUID, ArrayList<ItemStack>> inventories = new HashMap<UUID, ArrayList<ItemStack>>();
    private HashMap<UUID, Integer> cps = new HashMap<>();

    public StaffModeHandler(){
        createStaffModeItems();
        loadInventories();
    }

    public void disable(){
        saveInventories();
    }

    public void addCPSTracker(UUID uuid){
        cps.put(uuid, 0);
    }

    public void addCPS(UUID uuid){
        if(cps.containsKey(uuid)){
            cps.put(uuid, cps.get(uuid)+1);
        }
    }

    public int getCPS(UUID uuid){
        return cps.containsKey(uuid) ? cps.get(uuid) : 0;
    }

    public void removeCPSTracker(UUID uuid){
        if(cps.containsKey(uuid)){
            cps.remove(uuid);
        }
    }

    public boolean hasCPSTracker(UUID uuid){
        return cps.containsKey(uuid);
    }

    //used as a sudo 'is in staffmode' method (null if not in staff mode)
    public ArrayList<ItemStack> getInventory(UUID uuid){
        if(inventories.containsKey(uuid)){
            return inventories.get(uuid);
        }
        return null;
    }

    public void removeInventory(UUID uuid){
        if(inventories.containsKey(uuid)){
            inventories.remove(uuid);
        }
    }

    private void saveInventories(){
        StaffModule.getInstance().getStaffConfig().getConfig().set("inventories", null);
        for(UUID uuid : inventories.keySet()) {
            int i = 0;
            for(ItemStack is : inventories.get(uuid)){
                if(is!=null) {
                    StaffModule.getInstance().getStaffConfig().getConfig().set("inventories." + uuid + "." + i, is);
                    i++;
                }
            }
        }
        StaffModule.getInstance().getStaffConfig().save();
    }

    private void loadInventories(){
        if(StaffModule.getInstance().getStaffConfig().getConfig().getConfigurationSection("inventories")==null){
            return;
        }
        for(String s : StaffModule.getInstance().getStaffConfig().getConfig().getConfigurationSection("inventories").getKeys(false)){
            UUID uuid = UUID.fromString(s);
            ArrayList<ItemStack> temp = new ArrayList<>();
            for(String slot : StaffModule.getInstance().getStaffConfig().getConfig().getConfigurationSection("inventories." + s).getKeys(false)){
                ItemStack is = StaffModule.getInstance().getStaffConfig().getConfig().getItemStack("inventories." + s + "." + slot);
                temp.add(is);
            }
            inventories.put(uuid, temp);
        }
    }

    public void addInventory(UUID uuid, ArrayList<ItemStack> items){
        inventories.put(uuid, items);
    }

    public void createStaffModeItems(){
        ItemMeta im;
        ItemStack item;
        item = new ItemStack(Material.COMPASS);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Teleport to Cursor Location");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.EYE_OF_ENDER);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Teleports to Random Player");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getData());
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Toggles Visability");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.PAPER);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "View All Open Reports");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.SNOW_BALL);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Freezes the Selected Player");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.WATCH);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Finds the Clicks Per Second for Selected Player");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.CHEST);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "View Inventory of Selected Player");
        item.setItemMeta(im);
        staffModeItems.add(item);

        item = new ItemStack(Material.LEASH);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Mounts Your Player on the Selected Player");
        item.setItemMeta(im);
        staffModeItems.add(item);
    }


    public GadgetType getGadgetType(String s){
        s = ChatColor.stripColor(s);
        switch (s){
            case "Teleport to Cursor Location":
                return GadgetType.TELE_CURSOR;
            case "Teleports to Random Player":
                return GadgetType.TELE_RANDOM_PLAYER;
            case "Toggles Visability":
                return GadgetType.VISABILITY;
            case "View All Open Reports":
                return GadgetType.REPORTS;
            case "Freezes the Selected Player":
                return GadgetType.FREEZE;
            case "Finds the Clicks Per Second for Selected Player":
                return GadgetType.CPS;
            case "View Inventory of Selected Player":
                return GadgetType.TARGET_INVENTORY;
            case "Mounts Your Player on the Selected Player":
                return GadgetType.MOUNT;
            default:
                return null;
        }
    }

    public ArrayList<ItemStack> getStaffModeItems(){
        return staffModeItems;
    }
}
