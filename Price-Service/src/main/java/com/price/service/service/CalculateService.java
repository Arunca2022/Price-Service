package com.price.service.service;

import java.util.List;

import com.price.service.dto.InputRequest;
import com.price.service.entity.Invoice;


public interface CalculateService {
   
	public String processPaymentInvoice(InputRequest inputRequestDto);
	
	public List<Invoice> getAll();
	
	
}
