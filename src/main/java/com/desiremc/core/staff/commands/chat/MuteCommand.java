package com.desiremc.core.staff.commands.chat;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/18/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class MuteCommand extends DesireCommand {

    public MuteCommand(){
        super("mute");
        addAliases("m");
        setDescription("Mutes the currently unmuted chat.");
    }


    @Override
    public void execute(CommandSender sender, List<String> args){
        StaffModule.getInstance().muteChat(sender, true);
    }

}
