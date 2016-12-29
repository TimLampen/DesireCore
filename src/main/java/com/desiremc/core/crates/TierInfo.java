package com.desiremc.core.crates;

import com.desiremc.core.DesireCore;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class TierInfo {

    private final Material type;
    private final short data;
    private final String displayName;
    private final List<String> lore;
    private HashMap<ItemStack, Integer> rewards;
    private final int totalWeight;
    private final ItemStack displayItem;

    public TierInfo(Material type, short data, String displayName, List<String> lore, HashMap<ItemStack, Integer> rewards){
        this.type = type;
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);;
        this.data = data;
        this.lore = lore;
/*Finds the total weight of the config, so for example if there were two items and one had a weight of 6, while the other had a weight of 10
 * The total chance for the first item would be 6/(6+10) and the second item would be 10/(6+10) etc.
 */
        int tWeight = 0;
        for(Integer weight : rewards.values()){
            tWeight += weight;
        }
        this.totalWeight = tWeight;
        this.rewards = rewards;
        this.displayItem = generateDisplayItem();
        setRewardNames();
    }

    private void setRewardNames(){
        HashMap<ItemStack, Integer> temp = new HashMap<>();
        for(ItemStack is : rewards.keySet()){
            int weight = rewards.get(is);
            temp.put(is, weight);
        }
        rewards = temp;

    }

    public int getTotalWeight(){
        return totalWeight;
    }

    /*
     * Returns the the material type of the display item (the key)
     * */
    private Material getType(){
        return type;
    }
    /*
     * Returns the data value (for wool for example) of the display item (the key)
     * */
    private short getData(){
        return data;
    }
    /*
    * Returns the name of the display item (the key)
    * */
    public String getDisplayName(){
        return displayName;
    }

    /*
    * Return the lore of the display item (the key)
    * */
    private List<String> getLore(){
        return lore;
    }

    /**
    * Returns the display item (the key) in itemstack form
    * @param amount the amount of keys
    * */
    public ItemStack getDisplayItem(int amount){
        ItemStack temp = displayItem.clone();
        temp.setAmount(amount);
        return temp;
    }

    //Makes the 'key' that can be used on a crate
    private ItemStack generateDisplayItem(){
        ItemStack is = new ItemStack(getType(), 1, getData());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(getDisplayName());
        im.setLore(getLore());
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        is.setItemMeta(im);
        return is;
    }
    private Random ran = new Random();
    /*
    * Based on the weight of the different rewards, generates a single reward to give to the player.
    * */
    public ItemStack generateReward(){
        int weight = ran.nextInt(totalWeight)+1;
        int currentWeight = 0;
        for(ItemStack reward : getRewards().keySet()) {
            int rewardWeight = getRewards().get(reward);
            currentWeight += rewardWeight;
            if (currentWeight >= weight) {
                return reward;
            }
        }
        if(DesireCore.getInstance().isDebugEnabled()){
            Logger.error(ChatColor.RED + "(Crate Keys) Unable to find reward for " + displayName + "! With total weight: " + totalWeight);
        }
        return null;
    }

    public HashMap<ItemStack, Integer> getRewards(){
        return rewards;
    }
}
