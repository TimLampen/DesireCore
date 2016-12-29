package com.desiremc.core.timers;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLConfig;

import java.io.File;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class TimerConfig extends YMLConfig {

    public TimerConfig() {
        super(new File(DesireCore.getInstance().getDataFolder() + "/timers.yml"), false);
    }

}