package com.example.robet.rtrain.gson;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CityResponse{

	@SerializedName("city")
	private List<CityItem> city;

	public void setCity(List<CityItem> city){
		this.city = city;
	}

	public List<CityItem> getCity(){
		return city;
	}
}