package com.imlewis.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imlewis.tool.AssessmentTool;

@JsonPropertyOrder({"productId", "title", "colorSwatches", "nowPrice", "priceLabel"})
@JsonSerialize
public class Product {

	public static final String NOW_PRICE_DELIMITER = "to";
	public static final String GBP_CURRENCY = "£";
	public static final String TWO_DECIMAL_POINTS = "%.2f";
	public static final String NO_DECIMAL_POINTS = "%.0f";

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

	public String getNowPrice() {
		return GBP_CURRENCY + getNow();
	}

	public void setPriceLabel(String priceLabel) {
		this.priceLabel = priceLabel;
	}

	public String getPriceLabel() {
		return priceLabel;
	}

	@JsonIgnore
	public String getPriceLabelForNow() {
		return buildPriceLabelForNow();
	}

	@JsonIgnore
	public String getPriceLabelForNowThen() {
		return buildPriceLabelForNowThen();
	}

	@JsonIgnore
	public String getPriceLabelForDiscount() {
		return buildPriceLabelForDiscount();
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
		if(getPrice() == null)
			return String.valueOf(Float.MIN_VALUE);
		return filteredNowPrice(getPrice().getNow());
	}

	@JsonIgnore
	public float getNowFloat() {
		return AssessmentTool.getFloatValue(getNow());
	}

	@JsonIgnore
	public String getWas() {
		if(getPrice() == null)
			return String.valueOf(Float.MIN_VALUE);
		return getPrice().getWas();
	}

	@JsonIgnore
	public String getThen1() {
		if(getPrice() == null)
			return String.valueOf(Float.MIN_VALUE);
		return getPrice().getThen1();
	}

	@JsonIgnore
	public String getThen2() {
		if(getPrice() == null)
			return String.valueOf(Float.MIN_VALUE);
		return getPrice().getThen2();
	}

	@JsonIgnore
	public float getWasFloat() {
		return AssessmentTool.getFloatValue(getWas());
	}

	@JsonIgnore
	public boolean checkIfValidProducts() {
		return checkIfItsAValidProductReduction(getPrice());
	}

	@JsonIgnore
	public float getDifference() {
		return getWasFloat() - getNowFloat();
	}

	private boolean checkIfItsAValidProductReduction(Price price) {
		return StringUtils.isNotEmpty(getWas())
				&& (AssessmentTool.getFloatValue(getNow()) < AssessmentTool.getFloatValue(getWas()));
	}

	private String filteredNowPrice(JsonNode nowRequest) {
		String nowPriceString = StringUtils.isEmpty(nowRequest.asText()) ? nowRequest.toString() : nowRequest.asText();

		if (nowPriceString.contains(NOW_PRICE_DELIMITER)) {
			nowPriceString = nowRequest.get(NOW_PRICE_DELIMITER).asText();
		}
		return nowPriceString;
	}

	private String getFormattedPrice(Float price) {
		return price < 10 ? String.format(TWO_DECIMAL_POINTS, price) : String.format(NO_DECIMAL_POINTS, price);
	}

	private String buildPriceLabelForNow() {
		StringBuffer priceLabel = new StringBuffer("");

		if (StringUtils.isNotEmpty(getWas())) {
			priceLabel.append("Was ").append(GBP_CURRENCY).append(getFormattedPrice(getWasFloat())).append(", ");
			priceLabel.append("now ").append(getNowPrice());
		}
		return priceLabel.toString();
	}

	private String buildPriceLabelForNowThen() {
		StringBuffer priceLabel = new StringBuffer("");

		String thenPrice = StringUtils.isNotEmpty(getThen2()) ? getThen2()
				: (StringUtils.isNotEmpty(getThen1()) ? getThen1() : null);

		if (StringUtils.isNotEmpty(getWas())) {
			priceLabel.append("Was ").append(GBP_CURRENCY).append(getFormattedPrice(getWasFloat())).append(", ");
			if (StringUtils.isNotEmpty(thenPrice)) {
				priceLabel.append("then ").append(GBP_CURRENCY)
						.append(getFormattedPrice(AssessmentTool.getFloatValue(thenPrice))).append(", ");
			}
			priceLabel.append("now ").append(getNowPrice());
		}
		return priceLabel.toString();
	}

	private String buildPriceLabelForDiscount() {
		StringBuffer priceLabel = new StringBuffer("");

		if (StringUtils.isNotEmpty(getWas())) {
			float previousPrice = getWasFloat();
			float currentPrice = getNowFloat();
			if (previousPrice > 0) {
				int discount = (int) Math.round((currentPrice / previousPrice) * 100);
				priceLabel.append(discount).append("% off").append(" - now ").append(GBP_CURRENCY).append(currentPrice);
			}
		}
		return priceLabel.toString();
	}

}