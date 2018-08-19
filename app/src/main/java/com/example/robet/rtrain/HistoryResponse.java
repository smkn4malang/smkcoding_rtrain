package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse{

	@SerializedName("history")
	private List<HistoryItem> history;

	public void setHistory(List<HistoryItem> history){
		this.history = history;
	}

	public List<HistoryItem> getHistory(){
		return history;
	}

	@Override
 	public String toString(){
		return 
			"HistoryResponse{" + 
			"history = '" + history + '\'' + 
			"}";
		}
}