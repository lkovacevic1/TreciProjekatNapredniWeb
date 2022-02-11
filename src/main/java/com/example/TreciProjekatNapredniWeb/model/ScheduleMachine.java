package com.example.TreciProjekatNapredniWeb.model;

public class ScheduleMachine {

    private Long id;
    private String date;
    private String time;
    private String action;

    public ScheduleMachine(Long id, String date, String time, String action) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
