package com.price.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InputRequest(
	    @JsonProperty("billing_header") BillingHeader billingHeader,
	    @JsonProperty("billing_lines") List<BillingLine> billingLines
	) {}
