package com.price.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Priceinformation(
		 @JsonProperty("total_price") TotalPrice totalPrice,
		 @JsonProperty("base_price") TotalPrice basePrice ){
}
