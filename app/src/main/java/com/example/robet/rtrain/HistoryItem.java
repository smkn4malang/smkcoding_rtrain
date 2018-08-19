package com.example.robet.rtrain;

import com.google.gson.annotations.SerializedName;

public class HistoryItem{

	@SerializedName("item")
	private String item;

	@SerializedName("historyId")
	private String historyId;

	@SerializedName("qty")
	private String qty;

	public void setItem(String item){
		this.item = item;
	}

	public String getItem(){
		return item;
	}

	public void setHistoryId(String historyId){
		this.historyId = historyId;
	}

	public String getHistoryId(){
		return historyId;
	}

	public void setQty(String qty){
		this.qty = qty;
	}

	public String getQty(){
		return qty;
	}

	@Override
 	public String toString(){
		return 
			"HistoryItem{" + 
			"item = '" + item + '\'' + 
			",historyId = '" + historyId + '\'' + 
			",qty = '" + qty + '\'' + 
			"}";
		}
}