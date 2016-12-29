package com.desiremc.core.deathban.commands.db;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.deathban.DeathBanAccount;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sneling on 16/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
class ReviveCommand extends DesireCommand {

    public ReviveCommand() {
        super("revive");

        setPermission(Perm.DEATHBAN_COMMANDS_DB_REVIVE);
        setDescription("Revive a player");

        addArgument(new ArgInfo("player", ArgType.MANDATORY, ArgRequirement.OFFLINE_PLAYER));
    }

    @Override
    public void execute(final CommandSender sender, final List<String> args){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                DeathBanAccount account = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(
                        Bukkit.getOfflinePlayer(args.get(0)).getUniqueId().toString());

                if(!account.isCurrentlyBanned()){
                    sender.sendMessage(Lang.DEATHBAN_COMMAND_DB_REVIVE_ALIVE.replaceAll("%PLAYER%", Bukkit.getOfflinePlayer(args.get(0)).getName()));
                    return;
                }

                account.setLives(1);
                sender.sendMessage(Lang.DEATHBAN_COMMAND_DB_REVIVE_SUCCESS.replaceAll("%PLAYER%", Bukkit.getOfflinePlayer(args.get(0)).getName()));

                try {
                    account.updateRemoteData();
                } catch (SQLException e) {
                    sender.sendMessage(Lang.DEATHBAN_COMMAND_DB_REVIVE_ERROR);
                    e.printStackTrace();
                }
            }
        });
    }


}