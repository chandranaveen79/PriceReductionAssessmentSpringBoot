package com.imlewis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.imlewis.model.ProductGroup;

@RestController
public class AssessmentProductController {

	@Value("${listOfProductsURL}")
	private String productsURL;

	@GetMapping(value = "/priceReductionList")
	public @ResponseBody ProductGroup priceReductionList(
			@RequestParam(name = "labelType", required = false, defaultValue = "ShowWasNow") String labelType) {

		ProductGroup productGroup = new RestTemplate().getForObject(productsURL, ProductGroup.class);

		productGroup.getProducts().stream().forEach(product -> {
			switch (LabelType.valueOf(labelType.toUpperCase())) {
			case SHOWWASNOW: {
				product.setPriceLabel(product.buildPriceLabelForNow());
				break;
			}
			case SHOWWASTHENNOW: {
				product.setPriceLabel(product.buildPriceLabelForNowThen());
				break;
			}
			case SHOWPERCDSCOUNT: {
				product.setPriceLabel(product.buildPriceLabelForDiscount());
				break;
			}
			}
		});
		return productGroup;
	}
}

enum LabelType {
	SHOWWASNOW, SHOWWASTHENNOW, SHOWPERCDSCOUNT;
}
