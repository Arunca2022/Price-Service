package com.price.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BillingLineInformation(
		@JsonProperty("product_type") String productType,
		@JsonProperty("line_type") String lineType
		) {}
