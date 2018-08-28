package com.example.robet.rtrain;

import com.google.gson.annotations.SerializedName;

public class TrainItem{

	@SerializedName("seat")
	private String seat;

	@SerializedName("date")
	private String date;

	@SerializedName("booked")
	private int booked;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("category")
	private String category;

	public void setSeat(String seat){
		this.seat = seat;
	}

	public String getSeat(){
		return seat;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setBooked(int booked){
		this.booked = booked;
	}

	public int getBooked(){
		return booked;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	@Override
 	public String toString(){
		return 
			"TrainItem{" + 
			"seat = '" + seat + '\'' + 
			",date = '" + date + '\'' + 
			",booked = '" + booked + '\'' + 
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",time = '" + time + '\'' + 
			",category = '" + category + '\'' + 
			"}";
		}
}