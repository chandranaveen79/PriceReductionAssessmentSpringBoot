package com.imlewis.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imlewis.tool.AssessmentTool;

@JsonSerialize
public class ColorSwatch {
	private String basicColor;
	private String color;
	private String skuId;
	
	public String getRbgColor() {
		return AssessmentTool.getHexColor(getBasicColor());
	}
	public String getBasicColor() {
		return basicColor;
	}
	public void setBasicColor(String basicColor) {
		this.basicColor = basicColor;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
}
