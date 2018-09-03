package com.example.robet.rtrain.gson;

import com.google.gson.annotations.SerializedName;

public class CityItem{

	@SerializedName("name")
	private String name;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}