package com.almog.admatay;

import org.joda.time.DateTime;

public class Shift {
    public DateTime startTime;
    public DateTime endTime;

    public Shift(DateTime startTime, DateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
