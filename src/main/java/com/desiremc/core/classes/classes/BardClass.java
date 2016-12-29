package com.desiremc.core.classes.classes;

import com.desiremc.core.DesireCore;
import com.desiremc.core.classes.*;
import com.desiremc.core.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class BardClass extends HCFClass{
    private HashMap<UUID, Integer> oldLevels = new HashMap<UUID, Integer>();
    private int energyPerSec = 5;

    @Override
    public void classSpecificLoad() {
        if(ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("players.bard.exp")!=null) {
            for (String sUUID : ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("players.bard.exp").getKeys(false)) {
                UUID uuid = UUID.fromString(sUUID);
                int level = ClassModule.getInstance().getClassConfig().getConfig().getInt("players.bard.exp." + uuid);
                oldLevels.put(uuid, level);
            }
        }

        energyPerSec = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes.bard.general.energypersec");
    }

    @Override
    public void classSpecificSave() {
        for(UUID uuid: getPlayers()){
            ClassModule.getInstance().getClassConfig().getConfig().set("players.bard.exp." + uuid, oldLevels.get(uuid));
        }
        ClassModule.getInstance().getClassConfig().save();
    }

    @Override
    public void tick(){
        ArrayList<UUID> temp = getPlayers();
        if(temp==null){
            return;
        }
        for(UUID uuid : temp){
            Player bard = Bukkit.getPlayer(uuid);
            if(isApplicable(bard, getArmorType())) {
                addExp(bard);
                effect(bard);
                if(bard.getItemInHand()!=null) {
                    checkPassiveItems(bard, bard.getItemInHand().getType());
                }
                for (PotionEffect effect : getPots()) {
                    bard.addPotionEffect(effect);
                }
            }
            else{
                removePlayer(uuid);
            }
        }
    }


    @Override
    public ClassType getClassType() {
        return ClassType.BARD;
    }

    @Override
    public ArmorType getArmorType() {
        return ArmorType.GOLD;
    }

    @Override
    public void checkPassiveItems(Player player, Material item){
        if(!getPassiveItems().containsKey(item)){
            return;
        }
        PotionEffect type = getPassiveItems().get(item);
        addPotionEffect(player, new PotionEffect(type.getType(), 25, type.getAmplifier()));
    }

    @Override
    public void checkActiveItems(Player player, Material item) {
        if(!getActiveItems().containsKey(item)){
            return;
        }
        ActiveItem activeItem = getActiveItems().get(item);
        if(player.getLevel()<activeItem.getArg()){
            player.sendMessage(Lang.CLASSES_NO_ENERGY.replace("%ENERGY%", activeItem.getArg() + ""));
            return;
        }
        player.setLevel(player.getLevel()-activeItem.getArg());
        ItemStack is = player.getItemInHand();
        if(is.getAmount()>1){
            is.setAmount(is.getAmount()-1);
        }
        else{
            player.getInventory().removeItem(is);
        }
        player.updateInventory();
        for(Entity e : player.getNearbyEntities(15, 15, 15)) {
            if(e instanceof Player){
                Player person = (Player) e;
                addPotionEffect(person, activeItem.getEffect());
                
                //todo: add clause to check if the player is an enemy or ally, for now assuming it is always a teammate
            }
        }
    }

    @Override
    public void effect(Player player) {
        for (PotionEffect pot :getPots()) {
            addPotionEffect(player, pot);
        }
    }

    private void addExp(Player player){
        if(player.getLevel()>=100){
            return;
        }
        else if(player.getLevel()>100-energyPerSec){
            player.setLevel(100);
        }
        else{
            player.setLevel(player.getLevel()+energyPerSec);
        }
    }

}
