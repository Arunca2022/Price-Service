package com.price.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TotalPrice(
		@JsonProperty("amount") String amount,
		@JsonProperty("currency_code") String currencyCode) {}
