package com.desiremc.core.commands.args;

import com.desiremc.core.util.StringUtil;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public enum ArgRequirement {
    INTEGER,
    DOUBLE,
    PLAYER,
    BOOLEAN,
    OFFLINE_PLAYER,
    UUID;

    public String getDisplayName(){
        return StringUtil.capitalizeFirst(this.toString().toLowerCase());
    }

}