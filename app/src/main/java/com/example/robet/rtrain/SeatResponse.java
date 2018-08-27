package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SeatResponse{

	@SerializedName("seat")
	private List<SeatItem> seat;

	public void setSeat(List<SeatItem> seat){
		this.seat = seat;
	}

	public List<SeatItem> getSeat(){
		return seat;
	}
}