package com.desiremc.core.deathban;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.sql.AsyncMySQLRunnable;
import com.desiremc.core.sql.MySQLResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright © 2016 - Now Sneling
 * <p>
 * You are not allowed to copy any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class DeathBanAccount {

    private final String uuid;
    private int lives;
    private long currentBanExpire;

    public DeathBanAccount(String uuid) {
        this.uuid = uuid;
    }

    public final void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * This will overwrite the current ban with this new time.
     * @param expireIn The time in which the ban will expire in seconds.
     */
    public final void setBanExpireIn(long expireIn) {
        this.currentBanExpire = System.currentTimeMillis() / 1000 + expireIn;
    }

    public String getUuid() {
        return uuid;
    }

    public int getLives() {
        return lives;
    }

    /**
     *
     * @return At what time the ban expires.
     */
    public long getCurrentBanExpire() {
        return currentBanExpire;
    }

    /**
     *
     * @return The time left for the user's ban in seconds. ( Value is >= 0)
     */
    public long getBanTimeLeft(){
        return currentBanExpire - (System.currentTimeMillis() / 1000) < 0 ? 0 : currentBanExpire - (System.currentTimeMillis() / 1000);
    }

    public boolean isCurrentlyBanned(){
        return getLives() == 0 && getBanTimeLeft() > 0;
    }

    /**
     * This will set the variables in this class to the ones stored remotely.
     */
    void updateLocalData() throws SQLException {
        MySQLResponse response = DesireCore.getInstance().getSql().executeQuery("SELECT * FROM dmc_deathban_accounts WHERE id = ?", uuid);
        ResultSet set = response.getSet();

        if(!set.next()){ // NO DATA FOUND
            lives = 1;
            currentBanExpire = 1;
        }

        lives = set.getInt("lives");
        currentBanExpire = set.getLong("current_ban");
    }

    /**
     * This will set the database's values to the ones stored here.
     */
    public void updateRemoteData() throws SQLException {
        MySQLResponse response = DesireCore.getInstance().getSql().executeQuery("SELECT * FROM dmc_deathban_accounts WHERE id = ?", uuid);

        if(!response.getSet().next()){ // No data
            DesireCore.getInstance().getSql().asyncExecute(new AsyncMySQLRunnable() {
                @Override
                public void execute(MySQLResponse response) {

                }
            }, "INSERT INTO dmc_deathban_accounts (id, lives, current_Ban) VALUES (?, ?, ?)", uuid, lives, currentBanExpire);
        }else{
            DesireCore.getInstance().getSql().asyncExecuteUpdate(new AsyncMySQLRunnable() {
                @Override
                public void execute(MySQLResponse response) {

                }
            }, "UPDATE dmc_deathban_accounts SET lives=?, current_ban=? WHERE id=?", lives, currentBanExpire, uuid);
        }
    }

    public String getFormattedTime(){
        long timeLeft = getBanTimeLeft();

        long hours = timeLeft / 3600; // 1H
        timeLeft = timeLeft % 3600;
        int minutes = (int) (timeLeft / 60);
        timeLeft = timeLeft % 60;
        int seconds = (int) timeLeft;

        String message = Lang.DEATHBAN_TIME;

        if(hours == 0)
            message = message.replaceAll("%HOURS%", "");
        else if(hours == 1)
            message = message.replaceAll("%HOURS%", String.valueOf(1) + " hour");
        else
            message = message.replaceAll("%HOURS%", String.valueOf(hours) + " hours");

        if(minutes == 0)
            message = message.replaceAll("%MINUTES%", "");
        else if(minutes == 1)
            message = message.replaceAll("%MINUTES%", String.valueOf(1) + " minute");
        else
            message = message.replaceAll("%MINUTES%", String.valueOf(minutes) + " minutes");

        if(seconds == 0)
            message = message.replaceAll("%SECONDS%", "");
        else if(seconds == 1)
            message = message.replaceAll("%SECONDS%", String.valueOf(1) + " second");
        else
            message = message.replaceAll("%SECONDS%", String.valueOf(seconds) + " seconds");

        return message;
    }

}
