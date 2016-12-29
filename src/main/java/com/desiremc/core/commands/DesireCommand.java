package com.desiremc.core.commands;

import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.util.logger.LColor;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 *
 * To find an example on how to create a command, go to
 */
public abstract class DesireCommand {

    // SETUP
    private String name = "";
    private String description = "No Description Set";
    private final List<String> aliases = new ArrayList<>();
    private final List<DesireCommand> childCommands = new ArrayList<>();
    private final HashMap<Integer, ArgInfo> arguments = new HashMap<>();
    private Perm permission = Perm.GLOBAL_ALL;
    private Class senderType = CommandSender.class;

    /**
     * Constructor for a DesireCommand
     *
     * @param name The name of the command. This is used when executing the command.
     */
    public DesireCommand(String name) {
        setName(name);
    }

    final boolean run(CommandSender sender, List<String> args) {
        if (!(senderType.isInstance(sender))) {
            sender.sendMessage(Lang.COMMAND_INVALID_SENDER.replaceAll("%TYPE%", senderType.getName()));
            return false;
        }

        if (!PermChecker.has(sender, getPermission())) {
            sender.sendMessage(Lang.COMMAND_NO_PERM);
            return false;
        }

        for (int i : getArguments().keySet()) {
            ArgInfo argInfo = getArguments().get(i);

            if (args.size() > i) {
                if (argInfo.getType() == ArgType.OPTIONAL) {
                    if (argInfo.getDefault() != null || !argInfo.getDefault().equals("")) {
                        args.set(i, argInfo.getDefault());
                    }

                    // Else is ignored, so that if the command wants to branch out the command in parts according to the args length, it can.
                    // For example, /gamemode <mode> [Player]. The plugin could branch out so that if args.length == 2, it can get a player.
                } else {
                    sender.sendMessage(Lang.COMMAND_ARGUMENTS_MISSING);
                    return false;
                }
            }

            if (argInfo.validate(args.get(i))) {
                sender.sendMessage(Lang.COMMAND_ARGUMENTS_INVALID.replaceAll("%ARG%", argInfo.getName()).replaceAll("%TYPE%", argInfo.formatRequirements()));
                return false;
            }
        }

        Logger.debug(LColor.GREEN + "Command '" + this.getName() + "' is valid. Executing...");
        execute(sender, args);
        return true;
    }

    protected void execute(CommandSender sender, List<String> args) {
        HelpGenerator.sendHelp(sender, this, 1);
    }

    private void setName(String name) {
        this.name = name;
    }

    protected final void setDescription(String description) {
        this.description = description;
    }

    protected final void setPermission(Perm permission) {
        this.permission = permission;
    }

    public final String getDescription() {
        return description;
    }

    public final String getName() {
        return name;
    }

    public final List<String> getAliases() {
        return aliases;
    }

    protected final void addAliases(String... aliases) {
        for (String s : aliases)
            this.aliases.add(s.toLowerCase());
    }

    public final List<DesireCommand> getChildCommands() {
        return childCommands;
    }

    protected final void addChild(DesireCommand command) {
        childCommands.add(command);
        Logger.debug("Registered child '" + command.getName() + "' for command " + this.getName());
    }

    private HashMap<Integer, ArgInfo> getArguments() {
        return arguments;
    }

    protected final int addArgument(ArgInfo arg) {
        int id = getNextArgumentId();

        arguments.put(id, arg);

        return id;
    }

    private int getNextArgumentId() {
        return arguments.size();
    }

    public final Perm getPermission() {
        return permission;
    }

    public final boolean isForcePlayerExecuter() {
        return senderType == Player.class;
    }

    /**
     * The c#ommand will only execute if the CommandSender is an instance of <code>senderType</code>
     *
     * @param senderType The instance of CommandSender that you are expecting
     */
    protected final void setSenderType(Class senderType) {
        this.senderType = senderType;
    }

    public final boolean isVisibleFor(CommandSender sender) {
        return !(senderType.isInstance(sender)) || PermChecker.has(sender, getPermission());
    }

    public final String getUsage() {
        if (description.equals("") && getArguments().isEmpty())
            return "empty";

        if (!description.equals("") && getArguments().isEmpty())
            return "description";

        String res = getName();

        if (getArguments() == null || getArguments().values().isEmpty())
            return res;

        for (int i = 0; i < getArguments().values().size(); i++)
            res += " " + getArguments().get(i).format();

        return res;
    }

    /**
     * @param call String sent by the CommandSender
     * @return True if call equalsIgnoreCase the name of the command, or one of the aliases.
     */
    public boolean isValidCall(String call) {
        return name.equalsIgnoreCase(call) || aliases.contains(call.toLowerCase());
    }

}