package com.example.robet.rtrain.gson;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ManageTrainResponse {

	@SerializedName("train")
	private List<ManageTrainItem> train;

	public void setTrain(List<ManageTrainItem> train){
		this.train = train;
	}

	public List<ManageTrainItem> getTrain(){
		return train;
	}
}