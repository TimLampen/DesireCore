package com.desiremc.core.classes;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLConfig;

import java.io.File;

/**
 * Created by Timothy Lampen on 12/10/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ClassConfig extends YMLConfig {

    public ClassConfig() {
        super(new File(DesireCore.getInstance().getDataFolder(), "classes.yml"), true);
    }
}
