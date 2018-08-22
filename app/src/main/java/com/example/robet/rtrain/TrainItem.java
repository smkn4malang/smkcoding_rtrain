package com.example.robet.rtrain;

import com.google.gson.annotations.SerializedName;

public class TrainItem{

	@SerializedName("cars")
	private String cars;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("destination")
	private String destination;

	@SerializedName("id")
	private String id;

	@SerializedName("category")
	private String category;

	@SerializedName("depart")
	private String depart;

	@SerializedName("time")
	private String time;

	public void setCars(String cars){
		this.cars = cars;
	}

	public String getCars(){
		return cars;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDestination(String destination){
		this.destination = destination;
	}

	public String getDestination(){
		return destination;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setDepart(String depart){
		this.depart = depart;
	}

	public String getDepart(){
		return depart;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}
}