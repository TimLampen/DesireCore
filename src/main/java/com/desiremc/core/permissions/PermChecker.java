package com.desiremc.core.permissions;

import org.bukkit.permissions.Permissible;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class PermChecker {

    public static boolean has(Permissible p, Perm perm){
        return perm == null || perm == Perm.GLOBAL_ALL || p.hasPermission(perm.getID());
    }

    public static boolean has(Permissible p, String s) {
        return s == null || s.equals("") || p.hasPermission(s);
    }

}
