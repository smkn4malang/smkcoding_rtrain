package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TimeResponse{

    @SerializedName("time")
    private List<TimeItem> time;

    public void setTime(List<TimeItem> time){
        this.time = time;
    }

    public List<TimeItem> getTime(){
        return time;
    }
}