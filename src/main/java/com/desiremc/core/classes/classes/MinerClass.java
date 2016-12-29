package com.desiremc.core.classes.classes;

import com.desiremc.core.classes.ArmorType;
import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.classes.ClassType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 12/10/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class MinerClass extends HCFClass {
    
    private int yLevel;

    @Override
    public ClassType getClassType() {
        return ClassType.MINER;
    }

    @Override
    public ArmorType getArmorType() {
        return ArmorType.IRON;
    }

    @Override
        public void tick() {
        ArrayList<UUID> temp = getPlayers();
        for(UUID uuid : temp){
            Player miner = Bukkit.getPlayer(uuid);
            if(isApplicable(miner, getArmorType())) {
                effect(miner);
            }
            else{
                removePlayer(uuid);
            }
        }
    }

    @Override
    public void checkPassiveItems(Player player, Material is) {

    }

    @Override
    public void checkActiveItems(Player player, Material is) {

    }

    @Override
    public void effect(Player player) {
        for (PotionEffect effect : getPots()) {
            addPotionEffect(player, effect);
        }
        if(player.getLocation().getBlockY()<=yLevel){
            addPotionEffect(player, new PotionEffect(PotionEffectType.INVISIBILITY, 25, 0, false, false));
        }
    }

    @Override
    public void classSpecificLoad() {
        yLevel = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes.miner.general.ylevel", 20);
    }

    @Override
    public void classSpecificSave() {

    }

}
