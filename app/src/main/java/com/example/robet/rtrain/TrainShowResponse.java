package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrainShowResponse{

	@SerializedName("seat")
	private List<SeatItem> seat;

	@SerializedName("trainShow")
	private List<TrainShowItem> trainShow;

	public void setSeat(List<SeatItem> seat){
		this.seat = seat;
	}

	public List<SeatItem> getSeat(){
		return seat;
	}

	public void setTrainShow(List<TrainShowItem> trainShow){
		this.trainShow = trainShow;
	}

	public List<TrainShowItem> getTrainShow(){
		return trainShow;
	}
}