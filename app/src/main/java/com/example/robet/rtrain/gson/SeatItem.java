package com.example.robet.rtrain.gson;

import com.google.gson.annotations.SerializedName;

public class SeatItem{

	@SerializedName("seatNum")
	private String seatNum;

	@SerializedName("status")
	private boolean status;

	public void setSeatNum(String seatNum){
		this.seatNum = seatNum;
	}

	public String getSeatNum(){
		return seatNum;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}