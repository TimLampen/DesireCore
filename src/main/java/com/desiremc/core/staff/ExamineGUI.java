package com.desiremc.core.staff;

import com.desiremc.core.sql.Callback;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ExamineGUI {

    public static void createInv(final Player staff, final Player target, final Callback<Inventory> callback){
        getInfractionItem(target, new Callback<ItemStack>() {
            @Override
            public void onSuccess(ItemStack done) {
                Inventory inv = Bukkit.createInventory(staff, 54, ChatColor.GOLD + target.getName() + "'s Inventory" );
                int i = 0;
                //puts items in player's inventory into the examine inventory
                for(ItemStack is : target.getInventory().getContents()){
                    if(is!=null) {
                        inv.setItem(i , is);
                    }
                    i++;
                }
                i = 38;
                //puts player's armor into examine inventory
                for(ItemStack is : target.getInventory().getArmorContents()){
                    if(i==40){
                        if(target.getItemInHand()!=null){
                            ItemStack item = target.getItemInHand().clone();
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName(ChatColor.RED + "Item In Hand");
                            item.setItemMeta(im);
                            inv.setItem(i, item);
                        }
                        i++;
                    }
                    if(is!=null){
                        inv.setItem(i, is);
                    }
                    i++;
                }
                //adds the examine tools to the examine inventory
                inv.setItem(47, getFoodItem(target));
                inv.setItem(48, getGamemodeItem(target));
                inv.setItem(49, getIpItem(target));
                inv.setItem(50, getLocationItem(target));
                inv.setItem(51, done);
                callback.onSuccess(inv);
            }
        });
    }

    public static ItemStack getFoodItem(Player target){
        ItemStack is = new ItemStack(Material.COOKED_BEEF);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        im.setDisplayName(ChatColor.DARK_RED + "Food");
        lore.add(ChatColor.AQUA + "Food: " + ChatColor.GRAY + target.getFoodLevel() + "/20");
        lore.add(ChatColor.AQUA + "Health: " + ChatColor.GRAY + target.getHealth() + "/20");
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getIpItem(Player target){
        ItemStack is = new ItemStack(Material.PAPER);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        im.setDisplayName(ChatColor.DARK_RED + "IP Address");
        lore.add(ChatColor.GRAY + target.getAddress().getAddress().toString());
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getGamemodeItem(Player target){
        ItemStack is = new ItemStack(Material.GRASS);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        im.setDisplayName(ChatColor.DARK_RED + "Gamemode");
        lore.add(ChatColor.GRAY + target.getGameMode().toString());
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public static void getInfractionItem(final Player target, final Callback<ItemStack> callback) {
        StaffModule.getInstance().getDisciplineHandler().getTrackedPlayer(target, new Callback<TrackedPlayer>() {
            @Override
            public void onSuccess(TrackedPlayer done) {
                ItemStack is = new ItemStack(Material.COOKED_BEEF);
                ItemMeta im = is.getItemMeta();
                int warns = done.getWarns() == null ? 0 : done.getWarns().size();
                int reports = done.getReports() == null ? 0 : done.getReports().size();
                ArrayList<String> lore = new ArrayList<>();
                im.setDisplayName(ChatColor.DARK_RED + "Infractions");
                lore.add(ChatColor.AQUA + "Warns: " + ChatColor.GRAY + warns);
                lore.add(ChatColor.AQUA + "Reports: " + ChatColor.GRAY + reports);
                im.setLore(lore);
                is.setItemMeta(im);
                //already sync dont need to call sync again
                callback.onSuccess(is);
            }
        });
    }

    public static ItemStack getLocationItem(Player target){
        ItemStack is = new ItemStack(Material.WATCH);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        im.setDisplayName(ChatColor.DARK_RED + "Location");
        lore.add(ChatColor.AQUA + "World: " + ChatColor.GRAY + target.getWorld().getName());
        lore.add(ChatColor.AQUA + "Coords: " + ChatColor.GRAY + target.getLocation().getBlockX() + ", " + target.getLocation().getBlockY() + ", " + target.getLocation().getBlockZ());
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

}
