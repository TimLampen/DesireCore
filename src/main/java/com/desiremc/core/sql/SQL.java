package com.desiremc.core.sql;

import com.desiremc.core.DesireCore;
import org.bukkit.Bukkit;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 *
 * Please name all tables 'dmc_(package/the name of the function)_(table name)
 */
public class SQL {

    private final MySQLDatabase database;

    public SQL() {
        this.database = new MySQLDatabase();
    }

    public MySQLResponse executeQuery(String query, Object... sets){
        return database.executeQuery(query, sets);
    }

    public MySQLResponse execute(String query, Object... sets){
        return database.execute(query, sets);
    }

    public MySQLResponse executeUpdate(String query, Object... sets){
        return database.executeUpdate(query, sets);
    }

    public MySQLResponse exists(String table, String fieldName, String value, String fields){
        return database.exists(table, fieldName, value, fields);
    }

    public void asyncExecuteQuery(AsyncMySQLRunnable run, String query, Object... sets){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), run.setResponse(database.executeQuery(query, sets)));
    }

    public void asyncExecute(AsyncMySQLRunnable run, String query, Object... sets){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), run.setResponse(database.execute(query, sets)));
    }

    public void asyncExecuteUpdate(AsyncMySQLRunnable run, String query, Object... sets){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), run.setResponse(database.executeUpdate(query, sets)));
    }

    public void asyncExists(AsyncMySQLRunnable run, String table, String fieldName, String value, String fields){
        Bukkit.getScheduler().runTaskAsynchronously(DesireCore.getInstance(), run.setResponse(database.exists(table, fieldName, value, fields)));
    }

}