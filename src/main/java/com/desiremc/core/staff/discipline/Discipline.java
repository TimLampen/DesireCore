package com.desiremc.core.staff.discipline;

import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/26/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class Discipline {

    private UUID reportee;
    private String reporteeName;
    private ReportType reportType;
    private String reporter;
    private String reason;
    private String hackType;
    private long reportTime;

    public Discipline(UUID reportee, String reporteeName, String reporter, ReportType reportType, String type, String reason, long reportTime){
        this.reporter = reporter;
        this.reportType = reportType;
        this.reportee = reportee;
        this.reporteeName = reporteeName;
        this.reason = reason;
        this.hackType = type;
        this.reportTime = reportTime;
    }

    public Discipline(UUID reportee, String reporter, ReportType reportType, String type, String reason, long reportTime){
        this.reporter = reporter;
        this.reportee = reportee;
        this.reason = reason;
        this.reportType = reportType;
        this.hackType = type;
        this.reportTime = reportTime;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String getHackType(){
        return hackType;
    }

    public UUID getReportee() {
        return reportee;
    }

    //DO NOT ACCESS THIS METHOD UNLESS YOU KNOW WHAT YOU ARE DOING
    public String getReporteeName() {
        return reporteeName;
    }

    public String getReporter(){
        return reporter;
    }

    public ReportType getReportType(){
        return reportType;
    }

    public String getReason(){
        return reason;
    }

    public long getReportTime(){
        return reportTime;
    }
}
