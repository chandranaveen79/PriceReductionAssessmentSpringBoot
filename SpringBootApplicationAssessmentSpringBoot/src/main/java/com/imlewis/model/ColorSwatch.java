package com.imlewis.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imlewis.tool.AssessmentTool;

@JsonSerialize
@JsonPropertyOrder({ "color", "rgbColor", "skuId" })
public class ColorSwatch {
	private String basicColor;
	private String color;
	private String skuId;
	
	public String getRgbColor() {
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
