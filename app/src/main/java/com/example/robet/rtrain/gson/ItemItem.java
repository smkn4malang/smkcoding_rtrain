package com.example.robet.rtrain.gson;

import com.google.gson.annotations.SerializedName;

public class ItemItem{

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("pic")
	private String pic;

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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPic(String pic){
		this.pic = pic;
	}

	public String getPic(){
		return pic;
	}

	@Override
 	public String toString(){
		return 
			"ItemItem{" + 
			"price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",pic = '" + pic + '\'' + 
			"}";
		}
}