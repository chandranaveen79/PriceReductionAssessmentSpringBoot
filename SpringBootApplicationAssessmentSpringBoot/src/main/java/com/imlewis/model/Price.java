package com.imlewis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Price {
	private String was;
	@JsonProperty("now")
	private JsonNode now;
	private String then1;
	private String then2;
	
	public String getWas() {
		return was;
	}
	public void setWas(String was) {
		this.was = was;
	}
	public JsonNode getNow() {
		return now;
	}
	public void setNow(JsonNode now) {
		this.now = now;
	}
	public String getThen1() {
		return then1;
	}
	public void setThen1(String then1) {
		this.then1 = then1;
	}
	public String getThen2() {
		return then2;
	}
	public void setThen2(String then2) {
		this.then2 = then2;
	}
	
}
