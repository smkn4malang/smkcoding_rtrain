package com.example.robet.rtrain.gson;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemResponse{

	@SerializedName("item")
	private List<ItemItem> item;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}

	@Override
 	public String toString(){
		return 
			"ItemResponse{" + 
			"item = '" + item + '\'' + 
			"}";
		}
}