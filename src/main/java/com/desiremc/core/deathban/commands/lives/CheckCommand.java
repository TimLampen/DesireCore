package com.desiremc.core.deathban.commands.lives;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.deathban.DeathBanAccount;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    /**
     * Constructor for a DesireCommand
     */
    public CheckCommand() {
        super("check");

        setPermission(Perm.DEATHBAN_COMMANDS_LIVES_CHECK);
        setDescription("Check your lives or the lives of another player");
        setSenderType(Player.class);

        addArgument(new ArgInfo("player", ArgType.OPTIONAL, ArgRequirement.OFFLINE_PLAYER));
    }

    @Override
    public void execute(final CommandSender sender, List<String> args){
        final String prefix;
        final OfflinePlayer target;

        if(args.get(0) == null){
            if(sender instanceof Player)
                target = (Player) sender;
            else{
                sender.sendMessage(ChatColor.RED + "Please specify the player you're looking for.");
                return;
            }

            prefix = "Your";
        }else {
            target = Bukkit.getOfflinePlayer(args.get(0));
            prefix = target.getName() + "'s";
        }

        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                DeathBanAccount account = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(target.getUniqueId().toString());
                sender.sendMessage(Lang.DEATHBAN_COMMAND_CHECK.replaceAll("%PREFIX%", prefix).replaceAll("%AMOUNT%", String.valueOf(account.getLives())));
            }
        });
    }

}