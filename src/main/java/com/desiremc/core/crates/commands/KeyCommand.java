package com.desiremc.core.crates.commands;

import com.desiremc.core.commands.DesireCommand;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class KeyCommand  extends DesireCommand {

    public KeyCommand() {
        super("key");

        addChild(new GiveCommand());
        addChild(new GenerateCommand());
        addAliases("crate", "cratekey", "keycrate");
        setDescription("Commands related to crates and keys.");
    }
}