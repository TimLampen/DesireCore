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

import java.sql.SQLException;
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
class SendCommand extends DesireCommand {

    /**
     * Constructor for a DesireCommand
     */
    public SendCommand() {
        super("send");

        setSenderType(Player.class);
        setDescription("Send lives to other players");
        setPermission(Perm.DEATHBAN_COMMANDS_LIVES_GIVE);

        addArgument(new ArgInfo("player", ArgType.MANDATORY, ArgRequirement.OFFLINE_PLAYER));
        addArgument(new ArgInfo("amount", ArgType.MANDATORY, ArgRequirement.INTEGER));
    }

    @Override
    public void execute(final CommandSender sender, final List<String> args){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                Player p = (Player) sender;
                OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
                int amount = Integer.parseInt(args.get(1));

                DeathBanAccount senderAccount = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(p.getUniqueId().toString());

                if(senderAccount.getLives() < amount){ // Sender doesn't have enough lives
                    sender.sendMessage(Lang.DEATHBAN_COMMAND_GIVE_MISSING);
                    return;
                }

                DeathBanAccount receiverAccount = DesireCore.getInstance().getDeathBanModule().getAccountManager().getAccount(target.getUniqueId().toString());

                senderAccount.setLives(senderAccount.getLives() - amount);
                receiverAccount.setLives(receiverAccount.getLives() + amount);

                sender.sendMessage(Lang.DEATHBAN_COMMAND_GIVE_SUCCESS_SENDER.replaceAll("%LIVES%", String.valueOf(amount)).replaceAll("%PLAYER%", target.getName()));
                if(target.isOnline())
                    target.getPlayer().sendMessage(Lang.DEATHBAN_COMMAND_GIVE_SUCCESS_RECEIVER.replaceAll("%LIVES%", String.valueOf(amount)).replaceAll("%PLAYER%", p.getName()));

                try {
                    senderAccount.updateRemoteData();
                    receiverAccount.updateRemoteData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}