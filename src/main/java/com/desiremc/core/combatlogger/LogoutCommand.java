package com.desiremc.core.combatlogger;

import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Sneling on 26/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class LogoutCommand extends DesireCommand {

    /**
     * Constructor for a DesireCommand
     */
    public LogoutCommand() {
        super("logout");

        setDescription(Lang.COMBATLOG_LOGOUT_DESC);
        setSenderType(Player.class);
        setPermission(Perm.COMBATLOG_COMMAND_LOGOUT);
    }


    @Override
    public void execute(CommandSender sender, List<String> args){
        Player p = (Player) sender;

        CombatLoggerModule.getInstance().getManager().register(p, false);
        p.kickPlayer(Lang.COMBATLOG_LOGOUT);
    }

}