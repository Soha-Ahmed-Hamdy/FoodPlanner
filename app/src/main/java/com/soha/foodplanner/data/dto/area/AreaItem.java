package com.soha.foodplanner.data.dto.area;

import com.google.gson.annotations.SerializedName;

public class AreaItem {

	@SerializedName("strArea")
	private String strArea;

	public String getStrArea(){
		return strArea;
	}
}