package com.desiremc.core.crates;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLConfig;
import net.md_5.bungee.api.ChatColor;

import java.io.File;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class KeyConfig extends YMLConfig {
    private int crateSize;
    private int crateParticles;
    private int cratePeriod;
    private String crateParticle;
    private String crateParticleColor;
    private String crateRewardName;
    private String crateNameTag;

    public KeyConfig() {
        super(new File(DesireCore.getInstance().getDataFolder(), "cratekey.yml"), true);

        crateSize = getConfig().getInt("crate.size");
        crateParticles = getConfig().getInt("crate.particles");
        cratePeriod = getConfig().getInt("crate.period");
        crateParticle = getConfig().getString("crate.particle");
        crateParticleColor = getConfig().getString("crate.particlecolor");
        crateRewardName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("crate.rewardname"));
        crateNameTag = ChatColor.translateAlternateColorCodes('&', getConfig().getString("crate.nametag"));
    }

    public String getCrateNameTag(){
        return crateNameTag;
    }

    public int getCrateSize() {
        return crateSize;
    }

    public int getCrateParticles() {
        return crateParticles;
    }

    public int getCratePeriod() {
        return cratePeriod;
    }

    public String getCrateParticle() {
        return crateParticle;
    }

    public String getCrateParticleColor() {
        return crateParticleColor;
    }

    public String getRewardName() {
        return crateRewardName;
    }
}
