package com.desiremc.core.staff.discipline;

import com.desiremc.core.staff.TrackedPlayer;

import java.util.Comparator;

/**
 * Created by Timothy Lampen on 12/7/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class TrackedPlayerComparator implements Comparator<TrackedPlayer> {
    @Override
    public int compare(TrackedPlayer o1, TrackedPlayer o2) {
        return o1.getReports().size()-o2.getReports().size();
    }
}
