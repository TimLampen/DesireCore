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
class ReviveCommand extends DesireCommand {

    /**
     * Constructor for a DesireCommand
     */
    public ReviveCommand() {
        super("revive");

        setPermission(Perm.DEATHBAN_COMMANDS_LIVES_REVIVE);
        setSenderType(Player.class);
        setDescription("Revive another player");

        addArgument(new ArgInfo("player", ArgType.MANDATORY, ArgRequirement.OFFLINE_PLAYER));
    }

    @Override
    public void execute(final CommandSender sender, final List<String> args){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
                Player p = (Player) sender;

                DeathBanAccount targetAccount = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(target.getUniqueId().toString());

                if(targetAccount.getLives() >= 0){ // The target is still alive
                    p.sendMessage(Lang.DEATHBAN_COMMAND_REVIVE_ALIVE);
                    return;
                }

                DeathBanAccount senderAccount = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(p.getUniqueId().toString());

                if(senderAccount.getLives() < 1){ // The user has 0 lives, therefore cannot revive another player (if he revived him, he would get banned)
                    p.sendMessage(Lang.DEATHBAN_COMMAND_REVIVE_MISSING);
                    return;
                }

                targetAccount.setLives(targetAccount.getLives() + 1);
                senderAccount.setLives(senderAccount.getLives() - 1);

                sender.sendMessage(Lang.DEATHBAN_COMMAND_REVIVE_SUCCESS);
            }
        });
    }

}