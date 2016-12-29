package com.desiremc.core.crates;

import com.desiremc.core.DesireCore;
import com.desiremc.core.util.inventory.InventoryUtil;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class KeyInventory {
    private int[] panes = {0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,23,24,25,26};
    public void generateCrateInventory(final Player player, final TierInfo info){
        final Inventory inv = Bukkit.createInventory(player, 27, "");
        //The item below the itemstack that will be selected
        ItemStack selector = new ItemStack(Material.REDSTONE);
        ItemMeta im = selector.getItemMeta();
        im.setDisplayName("");
        selector.setItemMeta(im);
        //Glass panes to fill the inventory
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE);
        im = filler.getItemMeta();
        im.setDisplayName("");
        filler.setItemMeta(im);
        //putting everything in their initial positions
        for(int i : panes){
            inv.setItem(i, filler);
        }
        inv.setItem(22, selector);
        player.openInventory(inv);
        final ArrayList<ItemStack> rewards = new ArrayList<>();
        rewards.addAll(info.getRewards().keySet());
        //Small delay for effect
        new BukkitRunnable(){
            @Override
            public void run() {
                loopThroughItems(player, inv, rewards, info.generateReward(), 1, 2);
            }
        }.runTaskLater(DesireCore.getInstance(), 20);
    }
    private int[] items = new int[]{10, 11, 12, 13, 14, 15, 16};
    private int iterations = 46;
    private void loopThroughItems(final Player player, final Inventory inv, final ArrayList<ItemStack> rewards, final ItemStack winningItem, final int currentIteration, final long currentSpeed) {
        //If this is the first loop, shuffle everything
        if (currentIteration == 1) {
            Collections.shuffle(rewards);
            //swap the winning item into the winning position
            Collections.swap(rewards, rewards.indexOf(winningItem), iterations % rewards.size());
            int item = 0;
            for (int slot : items) {
                inv.setItem(slot, rewards.get(item));
                if (rewards.size() - 1 <= item) {
                    item = 0;
                } else {
                    item++;
                }
            }

        }
        //take the items from the slot behind and put them in the current slot
        for (int slot : items) {
            ItemStack currentItem = inv.getItem(slot);
            if (slot == 16) {
                boolean grabNew = rewards.size() - 1 == rewards.indexOf(currentItem);
                inv.setItem(slot, grabNew ? rewards.get(0) : rewards.get(rewards.indexOf(currentItem) + 1));
            } else {
                inv.setItem(slot, inv.getItem(slot + 1));
            }
        }
        //If it is done rolling (-3 because the winning item is put in the 10th slot instead of 13th)
        if (currentIteration >= iterations - 3) {
            flash(inv);
            //player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
            ItemStack item = winningItem.clone();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(null);
            im.setLore(null);
            item.setItemMeta(im);

            InventoryUtil.safeInventoryAdd(player, item, "crate winnings");
            return;
        }
        //todo: fix timing;
        new BukkitRunnable() {
            @Override
            public void run() {
                int newIteration = currentIteration + 1;
                long newSpeed = currentSpeed + 1;
                loopThroughItems(player, inv, rewards, winningItem, newIteration, newSpeed);
            }
        }.runTaskLater(DesireCore.getInstance(), currentSpeed / 8);
    }
    private Random ran = new Random();
    public void flash(final Inventory inv) {
        final long end = System.currentTimeMillis() + (1000*3);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(System.currentTimeMillis()>=end){
                    this.cancel();
                }
                int r = ran.nextInt(15)+1;
                ItemStack flashing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)r);
                for(int i : panes){
                    inv.setItem(i, flashing);
                }
            }
        }.runTaskTimer(DesireCore.getInstance(), 0, 5);
    }
}
