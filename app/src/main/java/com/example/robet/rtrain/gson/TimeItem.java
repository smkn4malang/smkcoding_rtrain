package com.example.robet.rtrain.gson;

import com.google.gson.annotations.SerializedName;

public class TimeItem{

    @SerializedName("time")
    private String time;

    @SerializedName("id")
    private String id;

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}