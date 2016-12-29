package com.desiremc.core.sql;

/**
 * Created by Timothy Lampen on 12/5/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public interface Callback<T> {

    void onSuccess(T done);
}
