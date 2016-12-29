package com.desiremc.core.classes.classes;

import com.desiremc.core.DesireCore;
import com.desiremc.core.classes.ActiveItem;
import com.desiremc.core.classes.ArmorType;
import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.classes.ClassType;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.util.CooldownUtil;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.BleedEntityEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 12/11/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
//todo: add some sort of range finder maybe? Could be a paid extra for this class
public class ArcherClass extends  HCFClass{
    private HashMap<UUID, Long> extraDmg = new HashMap<>();
    private int distanceForExtraDamage, extraDamageDuration;
    private double extraDamagePercent;

    @Override
    public ClassType getClassType() {
        return ClassType.ARCHER;
    }

    @Override
    public ArmorType getArmorType() {
        return ArmorType.LEATHER;
    }

    @Override
    public void tick() {
        ArrayList<UUID> temp = getPlayers();
        for(UUID uuid : temp){
            Player archer = Bukkit.getPlayer(uuid);
            if(isApplicable(archer, getArmorType())) {
                effect(archer);
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
    public void checkActiveItems(Player player, Material item) {
        if(!getActiveItems().containsKey(item)){
            return;
        }
        ActiveItem activeItem = getActiveItems().get(item);

        if(!CooldownUtil.isOnCooldown(player.getUniqueId(), "ARCHER_" + item.toString())){
            ItemStack is = player.getItemInHand();
            if(is.getAmount()>1){
                is.setAmount(is.getAmount()-1);
            }
            else{
                player.getInventory().removeItem(is);
            }
            player.updateInventory();
            addPotionEffect(player, activeItem.getEffect());
            CooldownUtil.addCooldown(player.getUniqueId(), "ARCHER_" + item.toString(), activeItem.getArg());
        }
        else{
            //todo: put in action bar
            player.sendMessage(Lang.CLASSES_ON_COOLDOWN.replace("%TIME%", CooldownUtil.getCooldown(player.getUniqueId(), "ARCHER_" + item.toString())));
        }
    }

    @Override
    public void effect(Player player) {
        for (PotionEffect effect : getPots()) {
            addPotionEffect(player, effect);
        }
    }

    @Override
    public void classSpecificLoad() {
        extraDamagePercent = ClassModule.getInstance().getClassConfig().getConfig().getDouble("classes.archer.general.extradmgpercent");
        distanceForExtraDamage = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes.archer.general.distanceforextradmg");
        extraDamageDuration = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes.archer.general.extradmgduration");

    }

    @Override
    public void classSpecificSave() {

    }

    public boolean isGettingExtraDmg(UUID uuid){
        if(extraDmg.containsKey(uuid)){
            long oldTime = extraDmg.get(uuid);
            if(System.currentTimeMillis()>=oldTime+(extraDamageDuration*1000)){
                extraDmg.remove(uuid);
                return false;
            }
            return true;
        }
        return false;
    }

    public int getDistanceForExtraDamage(){
        return distanceForExtraDamage;
    }

    public void addExtraDamage(Entity player){
        extraDmg.put(player.getUniqueId(), System.currentTimeMillis()+(extraDamageDuration*1000));
    }

    public double getExtraDamagePercent(){
        return extraDamagePercent;
    }

}
