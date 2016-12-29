package com.desiremc.core.sql;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public abstract class AsyncMySQLRunnable implements Runnable {

    private MySQLResponse response;

    public abstract void execute(MySQLResponse response);

    AsyncMySQLRunnable setResponse(MySQLResponse response){
        this.response = response;
        return this;
    }

    @Override
    public void run() {
        execute(response);
    }

}