package com.desiremc.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class StringUtil {


    public static String capitalizeFirst(String in){
        if(in.length() == 0)
            return "";

        return in.substring(0, 1).toUpperCase() + in.substring(1);
    }

    /**
     *
     * @param s String to be modified
     * @param length Number of characters that you want in your string
     * @param indicate If true, adds "..." add the end of the String
     * @return the shortened String
     */
    public static String shortenString(String s, int length, boolean indicate){
        if(s.length() <= length)
            return s;

        String res = s.substring(0, length);

        if(indicate)
            res += "...";

        return res;
    }

    public static String toString(List<String> list){
        return toString(list, ", ");
    }

    public static String toString(List<String> list, String separator){
        StringBuilder builder = new StringBuilder();

        for(String s: list)
            builder.append(separator).append(s);


        return builder.toString().replaceFirst(separator, "");
    }

    public static String toSentence(String[] strings){
        return toSentence(strings, 0);
    }

    public static String toSentence(String[] strings, int start){
        StringBuilder builder = new StringBuilder();

        for(int i = start; i < strings.length; i++)
            builder.append(strings[i]).append(" ");

        return builder.toString();
    }

    public static Location strToLoc(String s){
        String[] split = s.split("@");
        return new Location(Bukkit.getWorld(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }

    public static String locToString(Location l){
        return l.getWorld().getName() + "@" + l.getBlockX() + "@" + l.getBlockY() + "@" + l.getBlockZ();
    }

}
