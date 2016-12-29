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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
class CheckCommand extends DesireCommand {

    public CheckCommand(){
        super("check");

        setPermission(Perm.DEATHBAN_COMMANDS_LIVES_CHECK);
        setDescription("Check if a player is deathbanned.");
        setSenderType(Player.class);

        addArgument(new ArgInfo("player", ArgType.OPTIONAL, ArgRequirement.OFFLINE_PLAYER));
    }

    @Override
    public void execute(final CommandSender sender, final List<String> args){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
                DeathBanAccount account = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(target.getUniqueId().toString());

                if(account.isCurrentlyBanned()){
                    sender.sendMessage(Lang.DEATHBAN_COMMAND_DB_CHECK_TRUE.replaceAll("%PLAYER%", target.getName()));
                }else{
                    sender.sendMessage(Lang.DEATHBAN_COMMAND_DB_CHECK_FALSE.replaceAll("%PLAYER%", target.getName()));
                }
            }
        });
    }

}