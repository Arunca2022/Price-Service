package com.price.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BillingLine(
		 @JsonProperty("billing_line_information") BillingLineInformation billingLineInformation,
		 @JsonProperty("price_information") Priceinformation price_information
    ) {}
