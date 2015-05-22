package com.almog.admatay;

import org.joda.time.DateTime;

public class CustomList {
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String name;
    private DateTime shiftStart;
    private DateTime shiftEnd;

    public CustomList(int startHour, int startMinute, int endHour, int endMinute, String name, DateTime shiftStart, DateTime shiftEnd) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.name = name;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(DateTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public DateTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(DateTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }
}
