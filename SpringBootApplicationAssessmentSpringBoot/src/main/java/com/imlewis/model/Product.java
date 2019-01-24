package com.imlewis.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imlewis.tool.AssessmentTool;

@JsonPropertyOrder({ "productId", "title", "colorSwatches", "nowPrice", "priceLabel" })
@JsonSerialize
public class Product {

	public static final String NOW_PRICE_DELIMITER = "to";

	private String productId;
	private String title;
	private List<ColorSwatch> colorSwatches;
	private String priceLabel;
	private Price price;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPriceLabel(String priceLabel) {
		this.priceLabel = priceLabel;
	}

	public String getPriceLabel() {
		return priceLabel;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public List<ColorSwatch> getColorSwatches() {
		return colorSwatches;
	}

	public void setColorSwatches(List<ColorSwatch> colorSwatches) {
		this.colorSwatches = colorSwatches;
	}

	@JsonIgnore
	public String getNow() {
		return getPrice() == null ? String.valueOf(Float.MIN_VALUE) : filteredNowPrice(getPrice().getNow());
	}

	@JsonIgnore
	public String getWas() {
		return getPrice() == null ? String.valueOf(Float.MIN_VALUE) : getPrice().getWas();
	}

	@JsonIgnore
	public String getThen1() {
		return getPrice() == null ? String.valueOf(Float.MIN_VALUE) : getPrice().getThen1();
	}

	@JsonIgnore
	public String getThen2() {
		return getPrice() == null ? String.valueOf(Float.MIN_VALUE) : getPrice().getThen2();
	}

	@JsonIgnore
	public float getDifference() {
		return AssessmentTool.getFloatValue(getWas()) - AssessmentTool.getFloatValue(getNow());
	}

	private String filteredNowPrice(JsonNode nowRequest) {
		String nowPriceString = StringUtils.isEmpty(nowRequest.asText()) ? nowRequest.toString() : nowRequest.asText();

		if (nowPriceString.contains(NOW_PRICE_DELIMITER)) {
			nowPriceString = nowRequest.get(NOW_PRICE_DELIMITER).asText();
		}
		return nowPriceString;
	}

	@JsonIgnore
	public String buildPriceLabelForNow() {
		return String.join(StringUtils.EMPTY, "Was ", AssessmentTool.getFormattedPrice(getWas()), ", now",
				AssessmentTool.getFormattedPrice(getNow()));
	}

	@JsonIgnore
	public String buildPriceLabelForNowThen() {
		String priceLabel = StringUtils.EMPTY;

		String thenPrice = StringUtils.isNotEmpty(getThen2()) ? getThen2()
				: (StringUtils.isNotEmpty(getThen1()) ? getThen1() : null);

		priceLabel = String.join(StringUtils.EMPTY, "Was ", AssessmentTool.getFormattedPrice(getWas()), ", ");
		if (StringUtils.isNotEmpty(thenPrice)) {
			priceLabel = String.join(StringUtils.EMPTY, priceLabel, "then ",
					AssessmentTool.getFormattedPrice(thenPrice), ", ");
		}
		return String.join(StringUtils.EMPTY, priceLabel, "now ", AssessmentTool.getFormattedPrice(getNow()));
	}

	@JsonIgnore
	public String buildPriceLabelForDiscount() {
		String priceLabel = StringUtils.EMPTY;

		if (StringUtils.isNotEmpty(getWas())) {
			float previousPrice = AssessmentTool.getFloatValue(getWas());
			float currentPrice = AssessmentTool.getFloatValue(getNow());
			if (previousPrice > 0) {
				int discount = (int) Math.round((currentPrice / previousPrice) * 100);
				priceLabel = String.join(StringUtils.EMPTY, String.valueOf(discount), "% off - now ",
						AssessmentTool.getFormattedPrice(getNow()));
			}
		}
		return priceLabel;
	}

}