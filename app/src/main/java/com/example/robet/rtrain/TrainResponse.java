package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrainResponse{

	@SerializedName("train")
	private List<TrainItem> train;

	public void setTrain(List<TrainItem> train){
		this.train = train;
	}

	public List<TrainItem> getTrain(){
		return train;
	}

	@Override
 	public String toString(){
		return 
			"TrainResponse{" + 
			"train = '" + train + '\'' + 
			"}";
		}
}