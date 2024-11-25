package com.price.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BillingHeader(
		@JsonProperty("caller_name") String callerName,
	    @JsonProperty("billing_id") String billingId,
	    @JsonProperty("parent_billing_id") String parentBillingId
	    ) {}
