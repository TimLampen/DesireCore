package com.desiremc.core.staff;

import com.desiremc.core.staff.discipline.Discipline;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/25/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class TrackedPlayer {
    private UUID uuid;
    private String name;
    private ArrayList<Discipline> reports;
    private ArrayList<Discipline> warns;

    public TrackedPlayer(){
        reports = new ArrayList<Discipline>();
        warns = new ArrayList<Discipline>();
        name = null;
        uuid = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReports(ArrayList<Discipline> reports) {
        this.reports = reports;
    }

    public void setWarns(ArrayList<Discipline> warns) {
        this.warns = warns;
    }

    public ArrayList<Discipline> getReports() {
        return reports;

    }

    public void addReport(Discipline report) {
        reports.add(report);
    }

    public ArrayList<Discipline> getWarns() {
        return warns;
    }

    public void addWarn(Discipline warn) {
        warns.add(warn);
    }
}
