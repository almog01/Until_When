package com.almog.admatay;

public class CustomList {
    private String start;
    private String end;
    private String name;
    private String time;

    public CustomList(String start, String end, String name, String time) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.time = time;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return (time);
    }

    public void setTime(String time) {
        this.time = time;
    }
}
