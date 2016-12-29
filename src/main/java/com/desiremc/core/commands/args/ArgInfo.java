package com.desiremc.core.commands.args;

import com.desiremc.core.util.BooleanUtil;
import com.desiremc.core.util.numbers.DoubleUtil;
import com.desiremc.core.util.numbers.IntegerUtil;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class ArgInfo {

    private final String name;
    private String def;
    private final ArgType type;
    private final List<ArgRequirement> requirements;

    public ArgInfo(String name, ArgType type, ArgRequirement... requirements) {
        this.name = name;
        this.type = type;

        this.requirements = new ArrayList<>();
        Collections.addAll(this.requirements, requirements);
    }

    public final String getDefault(){
        return def;
    }

    public final void setDefault(String def) {
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public ArgType getType() {
        return type;
    }

    public List<ArgRequirement> getRequirements() {
        return requirements;
    }

    public boolean validate(String input){
        for(ArgRequirement req: requirements){
            switch (req) {
                case PLAYER:
                    if (Bukkit.getPlayer(input) == null)
                        return false;
                    break;
                case INTEGER:
                    if (!IntegerUtil.isInteger(input))
                        return false;
                    break;
                case BOOLEAN:
                    if(!BooleanUtil.isBoolean(input)){
                        return false;
                    }
                    break;
                case DOUBLE:
                    if (!DoubleUtil.isDouble(input))
                        return false;
                    break;
                case OFFLINE_PLAYER:
                    if(Bukkit.getOfflinePlayer(input) == null)
                        return false;
                    break;
                case UUID:
                    if(UUID.fromString(input)==null){
                        return false;
                    }
                    break;
            }
        }

        return true;
    }

    public String format(){
        if(getType() == ArgType.MANDATORY){
            return "<" + getName() + formatRequirements() + ">";
        }else {
            return "[" + getName() + formatRequirements() + "]";
        }
    }

    public String formatRequirements(){
        if(requirements == null || requirements.isEmpty())
            return "";

        String res = " (";

        for(ArgRequirement requirement: requirements){
            res += " & " + requirement.getDisplayName();
        }

        return res.replaceFirst(" & ", "") + ")";
    }

}