package com.example.robet.rtrain;

import com.google.gson.annotations.SerializedName;

public class UserShowItem {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("credit")
	private String credit;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

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

	public void setCredit(String credit){
		this.credit = credit;
	}

	public String getCredit(){
		return credit;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"UserShowItem{" +
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",credit = '" + credit + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}