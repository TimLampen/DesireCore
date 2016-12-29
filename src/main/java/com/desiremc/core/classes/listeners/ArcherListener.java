package com.desiremc.core.classes.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.classes.classes.ArcherClass;
import de.slikey.effectlib.effect.BleedEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by Shaun on 2016-12-16.
 */
public class ArcherListener implements Listener{

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Entity def =  event.getEntity();
            ArcherClass archerClass = ClassModule.getInstance().getArcherClass();
            if(archerClass.isGettingExtraDmg(def.getUniqueId())){
                double percent = 1;
                percent += archerClass.getExtraDamagePercent()/100;
                event.setDamage(event.getDamage()*percent);
                BleedEffect effect = new BleedEffect(DesireCore.getInstance().getEffectManager());
                effect.setEntity(event.getEntity());
                effect.iterations = 1;
                effect.hurt = false;
                effect.autoOrient = true;
                effect.start();
            }

            if(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && ((Arrow)event.getDamager()).getShooter() instanceof Player){
                Player attk = (Player)((Arrow)event.getDamager()).getShooter();
                if(archerClass.isApplicable(attk, archerClass.getArmorType()) && def.getLocation().distance(attk.getLocation()) >= archerClass.getDistanceForExtraDamage()){
                    archerClass.addExtraDamage(def);
                    Firework f = def.getWorld().spawn(def.getLocation().add(0, -1, 0), Firework.class);
                    FireworkMeta meta = f.getFireworkMeta();
                    meta.addEffect(FireworkEffect.builder().trail(true)
                            .with(FireworkEffect.Type.BURST)
                            .withFade(Color.ORANGE, Color.YELLOW, Color.WHITE)
                            .withColor(Color.RED, Color.WHITE).build());
                    meta.setPower(0);
                    f.setFireworkMeta(meta);
                    f.detonate();

                }
            }
        }
    }
}
