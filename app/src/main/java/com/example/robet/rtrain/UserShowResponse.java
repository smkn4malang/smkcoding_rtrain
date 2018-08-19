package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserShowResponse{

	@SerializedName("user")
	private List<UserShowItem> user;

	public void setUser(List<UserShowItem> user){
		this.user = user;
	}

	public List<UserShowItem> getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"UserShowResponse{" + 
			"user = '" + user + '\'' + 
			"}";
		}
}