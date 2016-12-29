package com.desiremc.core.staff.gadgets;

import com.desiremc.core.lang.Lang;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/21/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class FreezeHandler {
    private ArrayList<UUID> frozen = new ArrayList<UUID>();
    private ArrayList<UUID> loggedOut = new ArrayList<>();

    public void addFrozen(Player staff, Player target) {
        frozen.add(target.getUniqueId());
        addEffects(target);
    }

    public void removeFrozen(Player target){
        if(frozen.contains(target.getUniqueId())){
            frozen.remove(target.getUniqueId());
            removeEffects(target);
        }
    }

    public boolean isFrozen(UUID uuid) {
        return frozen.contains(uuid);
    }

    public void addLoggedOut(UUID uuid){
        loggedOut.add(uuid);
    }

    public void removeLoggedOut(UUID uuid){
        if(loggedOut.contains(uuid)){
            loggedOut.remove(uuid);
        }
    }

    public boolean isLoggedOut(UUID uuid){
        return loggedOut.contains(uuid);
    }

    private void addEffects(Player target){
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 128));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 128));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 128));
    }

    private void removeEffects(Player target){
        target.removePotionEffect(PotionEffectType.BLINDNESS);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.removePotionEffect(PotionEffectType.SLOW_DIGGING);

    }
}
