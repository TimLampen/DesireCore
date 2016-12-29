package com.desiremc.core.crates.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.crates.KeyModule;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.util.inventory.InventoryUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class GenerateCommand extends DesireCommand {

    public GenerateCommand(){
        super("generate");
        addAliases("gen", "gene");
        setPermission(Perm.KEY_CHEST_GENERATE);
        setSenderType(Player.class);
        setDescription("Creates a universal crate a player can place.");
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Player player = (Player)sender;
        player.sendMessage(Lang.COMMAND_KEY_GENERATE);
        InventoryUtil.safeInventoryAdd(player, generateKeyChest(), "crate");
    }

    //Generates a placeable ender chest that acts as a crate for all keys
    private ItemStack generateKeyChest(){
        ItemStack is = new ItemStack(Material.ENDER_CHEST);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(KeyModule.getInstance().getKeyConfig().getCrateNameTag());
        is.setItemMeta(im);
        return is;
    }
}
