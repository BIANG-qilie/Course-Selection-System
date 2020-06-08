package com.zhuboyang.www.po;

import java.sql.Date;
import java.sql.Time;

/**
 * @author BIANG
 */
public class Subject {
    private int id;
    private String subjectName;
    private Time startTime;
    private Time stopTime;
    private Time selectStartTime;
    private Time selectStopTime;
    private Date selectStartDate;
    private Date selectStopDate;
    private String belong;
    private int belongId;
    private int dayOfWeek;

    public Subject(int id, String subjectName,
                   Time startTime, Time stopTime,
                   Time selectStartTime, Time selectStopTime,
                   Date selectStartDate, Date selectStopDate,
                   int dayOfWeek, String belong, int belongId) {
        this.id = id;
        this.subjectName = subjectName;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.selectStartTime = selectStartTime;
        this.selectStopTime = selectStopTime;
        this.selectStartDate = selectStartDate;
        this.selectStopDate = selectStopDate;
        this.belong = belong;
        this.belongId = belongId;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public Time getStartTime() {
        return startTime;
    }
    public Time getStopTime() {
        return stopTime;
    }
    public Time getSelectStartTime() {
        return selectStartTime;
    }
    public Time getSelectStopTime() {
        return selectStopTime;
    }
    public Date getSelectStartDate() {
        return selectStartDate;
    }
    public Date getSelectStopDate() {
        return selectStopDate;
    }
    public String getBelong() {
        return belong;
    }
    public int getBelongId() {
        return belongId;
    }
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getSelectStart(){
        return getSelectStartDate()+" "+getSelectStartTime();
    }
    public String getSelectStop(){
        return getSelectStopDate()+" "+getSelectStopTime();
    }
}
