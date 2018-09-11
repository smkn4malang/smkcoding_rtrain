package com.example.robet.rtrain.gson;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse{

	@SerializedName("history")
	private List<HistoryItem> history;

	@SerializedName("info")
	private boolean info;

	public void setHistory(List<HistoryItem> history){
		this.history = history;
	}

	public List<HistoryItem> getHistory(){
		return history;
	}

	public void setInfo(boolean info){
		this.info = info;
	}

	public boolean getInfo(){
		return  info;
	}
}