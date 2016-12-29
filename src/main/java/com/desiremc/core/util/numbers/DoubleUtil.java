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
public class DoubleUtil {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }
    }

}