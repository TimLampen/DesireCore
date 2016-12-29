package com.desiremc.core.staff.commands.chat;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.permissions.Perm;

/**
 * Created by Timothy Lampen on 11/18/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ChatCommand extends DesireCommand{

    public ChatCommand(){
        super("chat");
        addAliases("c");
        setPermission(Perm.STAFF_CHAT_ALL);
        addChild(new ClearCommand());
        addChild(new MuteCommand());
        addChild(new UnMuteCommand());
        addChild(new SlowCommand());
    }
}
