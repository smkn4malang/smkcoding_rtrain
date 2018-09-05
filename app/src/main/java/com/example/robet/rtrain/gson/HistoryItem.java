package com.example.robet.rtrain.gson;

import com.google.gson.annotations.SerializedName;

public class HistoryItem{

	@SerializedName("date")
	private String date;

	@SerializedName("purchaseId")
	private String purchaseId;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setPurchaseId(String purchaseId){
		this.purchaseId = purchaseId;
	}

	public String getPurchaseId(){
		return purchaseId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}