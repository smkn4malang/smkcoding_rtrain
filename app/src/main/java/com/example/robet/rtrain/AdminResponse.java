package com.example.robet.rtrain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdminResponse{

	@SerializedName("admin")
	private List<AdminItem> admin;

	public void setAdmin(List<AdminItem> admin){
		this.admin = admin;
	}

	public List<AdminItem> getAdmin(){
		return admin;
	}

	@Override
 	public String toString(){
		return 
			"AdminResponse{" + 
			"admin = '" + admin + '\'' + 
			"}";
		}
}