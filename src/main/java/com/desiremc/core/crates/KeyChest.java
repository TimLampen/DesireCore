package com.desiremc.core.crates;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import com.sun.scenario.effect.Effect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;

/**
 * Created by Timothy Lampen on 12/3/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class KeyChest{

    private Location chest;
    private Entity title;
    private AnimatedBallEffect effect;
    public KeyChest(Location chest){
        this.chest = chest;
        Location loc = chest.clone();
        ArmorStand stand = (ArmorStand)chest
                .getWorld()
                .spawnEntity(
                        loc//
                                .add(.5, -1, .5),
                        EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setCustomName(KeyModule.getInstance().getKeyConfig().getCrateNameTag());
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        this.title = stand;
        Location a = chest.clone();
        AnimatedBallEffect eff = new AnimatedBallEffect(DesireCore.getInstance().getEffectManager());
        eff.setLocation(a.add(.5, -.1, .5));
        eff.iterations = -1;
        eff.size = KeyModule.getInstance().getKeyConfig().getCrateSize();
        eff.particles = KeyModule.getInstance().getKeyConfig().getCrateParticles();
        eff.particlesPerIteration = 5;
        eff.period = KeyModule.getInstance().getKeyConfig().getCratePeriod();
        eff.particle = ParticleEffect.valueOf(KeyModule.getInstance().getKeyConfig().getCrateParticle());
        eff.color = parseColor(KeyModule.getInstance().getKeyConfig().getCrateParticleColor());
        eff.yFactor = 1;
        eff.start();
        this.effect =eff;
    }

    public void destroy(){
        title.remove();
        effect.cancel();
    }

    public Location getChest() {
        return chest;
    }

    private Color parseColor(String s){
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(s.toLowerCase());
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = Color.ORANGE;
        }
        return color;
    }
}
