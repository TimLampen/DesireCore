package com.desiremc.core.crates;

import com.desiremc.core.DesireCore;
import com.desiremc.core.crates.commands.KeyCommand;
import com.desiremc.core.crates.listeners.CrateDestroyListener;
import com.desiremc.core.crates.listeners.CrateInteractListener;
import com.desiremc.core.crates.listeners.CratePlaceListener;
import com.desiremc.core.module.Module;
import com.desiremc.core.util.StringUtil;
import com.desiremc.core.util.logger.Logger;
import com.desiremc.core.util.numbers.IntegerUtil;
import de.slikey.effectlib.effect.HelixEffect;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */

public class KeyModule extends Module{
    //Where the string is the 'key' in the config
    private HashMap<String, TierInfo> keys = new HashMap<String, TierInfo>();
    private ArrayList<KeyChest> keyChests = new ArrayList<>();
    private KeyInventory keyInventory;
    private static KeyModule instance;
    private KeyConfig keyConfig;

    public KeyModule(){
        keyConfig = new KeyConfig();
        instance = this;
        loadKeyConfig();
        keyInventory = new KeyInventory();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        DesireCore.registerListeners(new CratePlaceListener(), new CrateDestroyListener(), new CrateInteractListener());
        registerCommand(new KeyCommand());
        loadCrates();
    }

    @Override
    public void onDisable() {
        saveCrates();
    }

    //Loads all the rewards and keys from the config into the arraylist tiers.
    private void loadKeyConfig(){
        FileConfiguration config = keyConfig.getConfig();
        for(String key : config.getConfigurationSection("keys").getKeys(false)){
            Logger.debug("(Crates) loading crate: " + key);
            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("keys." + key + ".displayName"));
            List<String> lore = config.getStringList("keys." + key + ".lore");
            ArrayList<String> tempLore = new ArrayList<String>();
            for(String s : lore){
                tempLore.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            short data = Short.parseShort(config.getString("keys." + key + ".data"));
            Material material = Material.getMaterial(config.getInt("keys." + key + ".material"));
            HashMap<ItemStack, Integer> rewards = new HashMap<ItemStack, Integer>();
            for(String s : config.getConfigurationSection("keys." + key + ".rewards").getKeys(false)){
                Logger.debug("(Crates) loading reward: " + s + " for crate: " + key);
                Material rMaterial = Material.getMaterial(config.getInt("keys." + key + ".rewards." + s + ".material"));
                short rData = Short.parseShort(config.getString("keys." + key + ".rewards." + s + ".data"));
                int weight = config.getInt("keys." + key + ".rewards." + s + ".weight");
                int amount = config.getInt("keys." + key + ".rewards." + s + ".amount");
                ItemStack reward = new ItemStack(rMaterial, amount, rData);
                if(config.getStringList("keys." + key + ".rewards." + s + ".enchants")!=null){
                    for(String ench : config.getStringList("keys." + key + ".rewards." + s + ".enchants")){
                        Enchantment enchantment = Enchantment.getByName(ench.split(":")[0].toUpperCase());
                        if(enchantment==null && IntegerUtil.isInteger(ench.split(":")[1])){
                            Logger.error("Unable to load enchantment: " + ench + " for item: " + s + " from set: " + key);
                            continue;
                        }
                        reward.addUnsafeEnchantment(enchantment, Integer.parseInt(ench.split(":")[1]));
                    }
                }
                rewards.put(reward, weight);

            }

            TierInfo info = new TierInfo(material, data, displayName, tempLore, rewards);
            getKeys().put(key, info);
        }
    }
    /**
     * Retuns if there is a 'key' under the same string
     */
    public boolean isValidKey(String s){
        return getKeys().containsKey(s);
    }

    /**
     * Retuns true if the itemstack is a key
     */
    public boolean isValidKey(ItemStack is){
        for(TierInfo info : getKeys().values()){
            if(info.getDisplayItem(1).isSimilar(is)){
                return true;
            }
        }
        return false;
    }

    //Returns the TierInfo paired with the itemstack key
    public TierInfo getTierInfo(ItemStack is){
        for(TierInfo info : getKeys().values()){
            if(info.getDisplayItem(1).isSimilar(is)){
                return info;
            }
        }
        return null;
    }

    /**
     *Retuns all the keys names and their information
     */
    public HashMap<String, TierInfo> getKeys(){
        return keys;
    }

    public TierInfo getKey(String s){
        if(isValidKey(s)){
            return getKeys().get(s);
        }
        return null;
    }

    private void loadCrates(){
        if(keyConfig.getConfig().getStringList("crates")!=null) {
            for (String s : keyConfig.getConfig().getStringList("crates")) {
                Location l = StringUtil.strToLoc(s);
                if (l != null) {
                    keyChests.add(new KeyChest(l));
                }
            }
        }
    }

    private void saveCrates(){
        ArrayList<String> temp = new ArrayList<String>();
        for(KeyChest chest : keyChests){
            temp.add(StringUtil.locToString(chest.getChest()));
            chest.destroy();
        }
        keyConfig.getConfig().set("crates", temp);
        keyConfig.save();

    }

    //Adds a location to the places where people can use keys
    public void addKeyChest(Location loc){
        getKeyChests().add(new KeyChest(loc));
    }

    //Checks to see if the itemstack is a crate that can be placed
    public boolean isKeyChest(ItemStack is){
        return is.getType()==Material.ENDER_CHEST && is.hasItemMeta() && is.getItemMeta().hasEnchant(Enchantment.DURABILITY);
    }

    //Checks to see if a block is a crate
    public boolean isKeyChest(Block b){
        for(KeyChest chest : getKeyChests()){
            if(chest.getChest().equals(b.getLocation())){
                return true;
            }
        }
        return false;
    }

    public KeyConfig getKeyConfig(){
        return keyConfig;
    }

    //Removes a crate from places where people can use their keys
    public void removeKeyChest(Location loc) {
        KeyChest target = null;
        for (KeyChest chest : getKeyChests()) {
            if (chest.getChest().equals(loc)) {
                target = chest;
            }
        }
        if (target != null) {
            target.destroy();
            getKeyChests().remove(target);
        }
    }

    //Returns all the crates in the server
    private ArrayList<KeyChest> getKeyChests(){
        return keyChests;
    }


    //returns the 'csgo' inventory of the crate
    public KeyInventory getKeyInventory(){
        return keyInventory;
    }

    public static KeyModule getInstance(){
        return instance;
    }

}
