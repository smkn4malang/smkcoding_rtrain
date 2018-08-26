package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShopResponse{

	@SerializedName("item")
	private List<ItemItem> item;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}
}