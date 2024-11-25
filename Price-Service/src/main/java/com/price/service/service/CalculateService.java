package com.price.service.service;

import com.price.service.dto.InputRequest;


public interface CalculateService {
   
	public int processPaymentInvoice(InputRequest inputRequestDto);
	
}
