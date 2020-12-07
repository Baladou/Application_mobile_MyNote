package com.example.application_note.Model;

public class Alarm {


    private int ID_Alarm;
    private String Date;
    private String Time;
    private long TimeInMillis;
    private int Note_ID;

    public Alarm(){}

    public Alarm( String date, String time,long timeinmillis, int note_ID) {
        Date = date;
        Time = time;
        TimeInMillis= timeinmillis;
        Note_ID = note_ID;
    }

    public int getID_Alarm() {
        return ID_Alarm;
    }

    public void setID_Alarm(int ID_Alarm) {
        this.ID_Alarm = ID_Alarm;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public long getTimeInMillis() {
        return TimeInMillis;
    }

    public void setTimeInMillis(long time) {
        TimeInMillis = time;
    }

    public int getNote_ID() {
        return Note_ID;
    }

    public void setNote_ID(int note_ID) {
        Note_ID = note_ID;
    }
}
