package com.desiremc.core.crates.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.crates.KeyModule;
import com.desiremc.core.crates.TierInfo;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.util.inventory.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class GiveCommand extends DesireCommand {

    public GiveCommand(){
        super("give");
        addAliases("g");
        addArgument(new ArgInfo("player", ArgType.MANDATORY, ArgRequirement.PLAYER));
        addArgument(new ArgInfo("keytype", ArgType.MANDATORY));
        addArgument(new ArgInfo("amount", ArgType.OPTIONAL, ArgRequirement.INTEGER));
        setPermission(Perm.KEY_GIVE);
        setDescription("Gives the specified player the specified amount of keys.");
    }
    /**
     * Executes with the command /key give <player> <keytype> [amount]
     */
    @Override
    public void execute(CommandSender sender, List<String> args){
        Player target = Bukkit.getPlayer(args.get(0));
        String keyType = args.get(1);
        int amount = args.size()==3 ? Integer.parseInt(args.get(2)) : 1;
        if(KeyModule.getInstance().isValidKey(keyType)){
            TierInfo info = KeyModule.getInstance().getKey(keyType);
            ItemStack key = info.getDisplayItem(amount);
            InventoryUtil.safeInventoryAdd(target, key, "keys");
            sender.sendMessage(Lang.COMMAND_KEY_GIVEN.replaceAll("%PLAYER%", target.getName()).replaceAll("%AMOUNT%", amount + ""));
            target.sendMessage(Lang.COMMAND_KEY_RECIEVE.replaceAll("%PLAYER%", sender.getName()).replaceAll("%AMOUNT%", amount + ""));
        }
        else{
            sender.sendMessage(Lang.COMMAND_KEY_NOT_VALID);
        }
    }
}