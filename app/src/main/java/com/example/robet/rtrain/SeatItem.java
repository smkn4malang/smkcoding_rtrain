package com.example.robet.rtrain;

import com.google.gson.annotations.SerializedName;

public class SeatItem{

	@SerializedName("seat")
	private String seat;

	@SerializedName("booked")
	private int booked;

	public void setSeat(String seat){
		this.seat = seat;
	}

	public String getSeat(){
		return seat;
	}

	public void setBooked(int booked){
		this.booked = booked;
	}

	public int getBooked(){
		return booked;
	}
}