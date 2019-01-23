package com.imlewis.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imlewis.tool.AssessmentTool;

@JsonSerialize
public class ProductGroup {

	List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
		filterForEligibleProductsAndSort();
	}

	private void filterForEligibleProductsAndSort() {
		products = products.stream()
				.filter(product -> AssessmentTool.getFloatValue(product.getNow()) < AssessmentTool
						.getFloatValue(product.getWas()))
				.sorted((product1, product2) -> Float.compare(product1.getDifference(), product2.getDifference())
						* (-1))
				.collect(Collectors.toList());
	}
}
