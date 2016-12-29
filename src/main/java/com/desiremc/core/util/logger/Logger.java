package com.desiremc.core.util.logger;

import com.desiremc.core.DesireCore;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class Logger {

    private static final String ERR = LColor.RED + "[ERROR] ";
    private static final String DEBUG = LColor.CYAN + "[DEBUG] " + LColor.RESET;

    public static void log(Object msg){
        print("[Core] " + msg + LColor.RESET);
    }

    public static void debug(Object msg) {
        if (DesireCore.getInstance().isDebugEnabled())
            print(DEBUG + "[Core] " + msg + LColor.RESET);
    }

    public static void error(Object msg){
        print(ERR + "[Core] " + msg + LColor.RESET);
    }

    public static void println(Object msg){
        System.out.println(msg);
    }

    public static void print(Object msg){
        System.out.print(msg);
    }

}
