package com.desiremc.core.lang;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLClassConfig;
import com.desiremc.core.util.logger.Logger;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */

// To add a Language entry, simply add a line and give it a default value.
// Do not modify String from others, unless for minor mistakes such as spelling.
public class Lang extends YMLClassConfig {

    /*COLORS FOR ARGUMENTS (like things you replace with .replace(%BLAWBLAW%, object)
    player = &6
    number = &b
    other = &6

    COLORS FOR CMDS
    successful cmd = &a
    not successful cmd = &c
    update / change  = &7
    */
    public static String

    PREFIX = "&dDesireMC &8&l> ",


    CLASSES_ON_COOLDOWN = PREFIX + "&cYou have an existing cooldown of &b%TIME%s &cfor this item.",
    CLASSES_NO_ENERGY = PREFIX + "&cYou do not have &b%ENERGY% &cenergy to use this item!",

    // DEFAULTS FOR COMMANDS//
    COMMAND_NO_PERM = PREFIX + "&cYou are not allowed to run this command.",
    COMMAND_ARGUMENTS_MISSING = PREFIX + "&cYou are missing arguments.",
    COMMAND_ARGUMENTS_INVALID = PREFIX + "&cPlease use a &6%TYPE% &cfor argument &6%ARG%&7.",
    COMMAND_INVALID_SENDER = PREFIX +"&cYou must be a &6%TYPE% to be able to use this command.",
    COMMAND_HELP_NONE = PREFIX + "&cThere is no help &7for this page",
    COMMAND_HELP_NO_PERM = PREFIX + "&cYou are not allowed to view the help for this command.",

    COMMAND_KEY_NOT_VALID = PREFIX + "&cThere iscno crate key under the name you specified.",
    COMMAND_KEY_GIVEN = PREFIX + "&aYou have given &6%PLAYER% &b%AMOUNT% &akey(s), they have been added to their inventory.",
    COMMAND_KEY_RECIEVE = PREFIX + "&aYou have recieved &b%AMOUNT% &akey(s) from &6%PLAYER% &a.",
    COMMAND_KEY_GENERATE = PREFIX +  "&aYou have generated a universal crate and it has been added to your inventory.",
    LISTENER_CRATE_PLACE = PREFIX + "&aYou have placed a crate which players can right click with their keys.",
    LISTENER_INTERACT_CRATE_NO_KEY = PREFIX + "&cYou have to have a crate key to open this box!",
    LISTENER_KEY_NO_PERM = PREFIX + "&cYou do not have permission to interact with the crate in that way.",
    LISTENER_CRATE_DESTROY = PREFIX + "&cYou have removed this crate.",
    REWARD_PREVIEW_TITLE = PREFIX + "&9%KEY% &8Key Rewards",

    INVENTORY_FULL_ITEM_DROP = PREFIX + "&cYour inventory is currently full so the &6%TYPE% &cdropped to the ground at your feet.",
    INVENTORY_FULL = PREFIX + "&cYour inventory is full.",

    ECO_NO_MONEY = PREFIX + "&cYou do not have enough money to purchase this item.",

    WRENCH_GIVE = PREFIX + "&aYou have given youself a wrench, it has been added to your inventory.",
    WRENCH_BREAK_BAD_BLOCK = PREFIX + "&cYou cannot break that block with a wrench.",

    SHOP_SIGN_NO_PERM = PREFIX + "&cYou do not have permission to create a sign shop.",
    SHOP_ITEM_NULL = PREFIX + "&cUnable to create shop sign because &6%STRING% &cis not an item.",
    SHOP_PRICE_NULL = PREFIX + "&cUnable to create shop because &6%STRING% &cis not a price.",
    SHOP_SIGN_CREATE = PREFIX + "&aYou have created a shop sign.",
    SHOP_SIGN_NOT_ENOUGH_ITEMS = PREFIX + "&cYou do not have enough items to sell.",
    SHOP_SELL_ITEMS = PREFIX + "&aYou have sold &b%AMOUNT% &6%TYPE% &ato the store.",
    SHOP_BUY_ITEMS = PREFIX + "&aYou have bought &b%AMOUNT% &6%TYPE% &afrom the store.",
    SHOP_SIGN_NO_INFO= PREFIX + "&cYou have not put in enough information to create this sign",

    DEATHBAN_TIME = PREFIX + "%HOURS% %MINUTES% %SECONDS%",
    DEATHBAN_COMMAND_CHECK = PREFIX + "&a%PREFIX% &7lives: &b%AMOUNT%",
    DEATHBAN_COMMAND_GIVE_MISSING = PREFIX + "&cYou do not have enough lives.",
    DEATHBAN_COMMAND_GIVE_SUCCESS_SENDER = PREFIX + "&aYou sent &b%LIVES% &alives to &6%PLAYER%.",
    DEATHBAN_COMMAND_GIVE_SUCCESS_RECEIVER = PREFIX + "&aYou received &b%LIVES%&a lives from &6%PLAYER%",
    DEATHBAN_COMMAND_REVIVE_MISSING = PREFIX + "&cYou do not have enough lives to revive somenone.",
    DEATHBAN_COMMAND_REVIVE_ALIVE = PREFIX + "&cYou cannot revive someone who's still alive... unless they're dead on the inside?",
    DEATHBAN_COMMAND_REVIVE_SUCCESS = PREFIX + "&aYou revived &6%PLAYER%&a, reducing your lives by &a1.",
    DEATHBAN_COMMAND_ADD = PREFIX + "&b%LIVES% &7lives were added to your acount.",
    DEATHBAN_COMMAND_SET_SUCCESS = PREFIX + "&7You now have &b%LIVES% &alives.",
    DEATHBAN_COMMAND_SET_SUCCESS_CONFIRM = PREFIX + "&aSet &6%PLAYER%&a's account to &b%LIVES% &alives.",
    DEATHBAN_COMMAND_DB_CHECK_TRUE = PREFIX + "&6%PLAYER% &ais currently banned.",
    DEATHBAN_COMMAND_DB_CHECK_FALSE = PREFIX + "&6%PLAYER% &aisn't currently banned.",
    DEATHBAN_COMMAND_DB_REVIVE_ALIVE = PREFIX + "&6%PLAYER% &cis still alive.",
    DEATHBAN_COMMAND_DB_REVIVE_SUCCESS = PREFIX + "&aRevived &6%PLAYER%.",
    DEATHBAN_COMMAND_DB_REVIVE_ERROR = PREFIX + "&cThere was an error while updating the remote data.",
    DEATHBAN_LOGIN_BANNED = PREFIX + "&cYou are dead. Time left: &b%TIME%",

    COMBATLOG_LOGOUT = PREFIX + "You logged out with /logout",
    COMBATLOG_LOGOUT_DESC = PREFIX + "Logout of the Server Safely",

    ENDMANAGEMENT_COMMAND = PREFIX + "&cAll enderchests now %BOOLEAN% be opened by players",
    ENDMANAGEMENT_DENY_OPEN = PREFIX + "&cEnderchests are currently disabled on this server.",

    STAFF_COMMAND_TRACK_PLAYER = PREFIX + "&aYou have %TRACK% &b%PLAYER% &afor the reason: &6%REASON%&a.",
    STAFF_COMMAND_WARN_TARGET = PREFIX +  "&cYou have been warned by &b%PLAYER% &cfor the reason: &6%REASON%&c. It has been added to your file.",
    STAFF_COMMAND_CLEARCHAT = PREFIX + "&aThe entire chat has been cleared by &b%PLAYER%&a.",
    STAFF_COMMAND_CANT_MUTE = PREFIX + "&cYou cannot do that as the server is already %TRACK%.",
    STAFF_COMMAND_SLOW_CHAT = PREFIX + "&aYou have slowed the chat for &6%TIME% &aseconds.",
    STAFF_COMMAND_MUTE = PREFIX + "&7You have &6%TRACK% &7the chat.",
    STAFF_COMMAND_TOGGLE_ALERT = PREFIX + "&aYou have toggled your alerts &6%ALERT%&a.",
    STAFF_COMMAND_STAFF_LIST = PREFIX + "&b%PLAYER%&a - &6%PREFIX%",
    STAFF_COMMAND_STAFFMODE_ACTIVATE = PREFIX + "&aYou have activated staff mode.",
    STAFF_COMMAND_STAFFMODE_DEACTIVATE = PREFIX + "&cYou have deactivated staff mode.",
    STAFF_LISTENER_STILL_MUTE = PREFIX + "&cYou are still currently muted in the chat for another &b%TIME% &cseconds.",
    STAFF_LISTENER_NEW_REPORT = PREFIX + "&6%REPORTEE% &7has been &6%TRACK% &7by &6%REPORTER% &7for the reason: &6%REASON%&a.",
    STAFF_LISTENER_NEW_NAME = PREFIX + "&6%PLAYER% &7has changed their name from &6%NAME%&7.",
    STAFF_LISTENER_VEIN_MINER = PREFIX + "&6%PLAYER% &7has found a vein of &6%BLOCKS% %TYPE%'s&7. ",
    STAFF_LISTENER_RANDOM_TELE = PREFIX + "&7You have randomly teleported to &6%TARGET%",
    STAFF_LISTENER_CURSOR_OUT_OF_RANGE = PREFIX + "&cCannot teleport to the specified block because it is too far!",
    STAFF_LISTENER_MOUNT =PREFIX + "&7You have mounted onto your selected player.",
    STAFF_LISTENER_PLAYER_FROZEN = PREFIX + "&cYou have been frozen by &b%PLAYER%, please do not log out.",
    STAFF_LISTENER_TARGET_FROZEN = PREFIX + "&aYou have frozen &6%PLAYER%&a, he cannot move.",
    STAFF_LISTENER_PLAYER_UNFROZEN = PREFIX + "&aYou have been unfrozen by &6%PLAYER%.",
    STAFF_LISTENER_TARGET_UNFROZEN = PREFIX + "&aYou have unfrozen &6%PLAYER%&a, he can now move.",
    STAFF_LISETNER_INSERT_REASON = PREFIX + "&7Type your reason for this disciplinary action.",
    STAFF_MODE_INVISIBILITY_TOGGLE_ON = PREFIX + "&7You are now invisible to all players. Note that this change will be reverted once you log out.",
    STAFF_MODE_INVISIBILITY_TOGGLE_OFF = PREFIX + "&7You have turned off your invisibility.",
    STAFF_MODE_CPS = PREFIX + "&7Target clicked &b%CLICKS%&7 times.";
//
    public Lang(){
        super(new File(DesireCore.getInstance().getDataFolder() + "/lang.yml"));
    }

    @Override
    protected void loadFromFile(){
        try {
            for (Field f : getClass().getDeclaredFields()) {
                if (!getConfig().isSet(toID(f.getName())))
                    getConfig().set(toID(f.getName()), f.get(getClass()));
                else
                    f.set(getClass(), ChatColor.translateAlternateColorCodes('&', getConfig().getString(toID(f.getName()))));
            }
        }catch (IllegalAccessException e){
            Logger.error("An error occured while loading the config.yml: ");
            e.printStackTrace();
        }
    }

}