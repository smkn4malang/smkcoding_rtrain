package com.example.robet.rtrain.gson;

import com.google.gson.annotations.SerializedName;

public class CartItem{

	@SerializedName("num")
	private int num;

	public void setNum(int num){
		this.num = num;
	}

	public int getNum(){
		return num;
	}
}