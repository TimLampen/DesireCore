package com.desiremc.core.deathban.commands.db;

import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.permissions.Perm;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class DeathBanCommand extends DesireCommand {

    /**
     * Constructor for a DesireCommand
     */
    public DeathBanCommand() {
        super("deathban");

        setPermission(Perm.DEATHBAN_COMMANDS_DEATHBAN);
        setDescription("[DB] - DeathBan's Main Command");

        addChild(new CheckCommand());
        addChild(new ReviveCommand());
    }

}