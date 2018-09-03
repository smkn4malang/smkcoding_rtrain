package com.example.robet.rtrain.gson;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartResponse{

	@SerializedName("cart")
	private List<CartItem> cart;

	public void setCart(List<CartItem> cart){
		this.cart = cart;
	}

	public List<CartItem> getCart(){
		return cart;
	}
}