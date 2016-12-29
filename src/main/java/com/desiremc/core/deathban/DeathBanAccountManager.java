package com.desiremc.core.deathban;

import com.desiremc.core.util.logger.Logger;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Sneling on 13/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class DeathBanAccountManager {

    private HashMap<String, DeathBanAccount> accounts = new HashMap<>();

    public DeathBanAccount getAccount(String uuid){
        if(accounts.containsKey(uuid))
            return accounts.get(uuid);

        DeathBanAccount account = new DeathBanAccount(uuid);

        try {
            account.updateLocalData();
        } catch (SQLException e) {
            Logger.error("[DB] An error occured while fetching data from SQL: ");
            e.printStackTrace();
        }

        accounts.put(uuid, account);
        return account;
    }

}