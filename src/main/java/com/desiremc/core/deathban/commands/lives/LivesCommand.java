package com.desiremc.core.deathban.commands.lives;

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
public class LivesCommand extends DesireCommand {

    /**
     * Constructor for a DesireCommand
     */
    public LivesCommand() {
        super("lives");

        setPermission(Perm.DEATHBAN_COMMANDS_LIVES);
        setDescription("[DB] - Lives' Main Command");

        addChild(new CheckCommand());
        addChild(new ReviveCommand());
        addChild(new SendCommand());
        addChild(new AddCommand());
        addChild(new SetCommand());
    }

}