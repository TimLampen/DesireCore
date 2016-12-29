package com.desiremc.core.util.numbers;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class IntegerUtil {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }
    }

    public static int getInt(String s, int d){
        try{
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            return d;
        }
    }

}