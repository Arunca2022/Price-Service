package com.price.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.price.service.dto.InputRequest;
import com.price.service.dto.InvoiceRequest;
import com.price.service.service.CalculateService;

@Service
public class CalculateServiceImpl implements CalculateService {
	
	
	@Autowired
    private RestTemplate restTemplate;

	@Override
	public int processPaymentInvoice(InputRequest inputRequestDto) {
		
		String url = "http://localhost:8081/invoice"; 
		InvoiceRequest invoiceRequest = new InvoiceRequest("12800");
		
		try {
			String postForObject = restTemplate.postForObject(url, invoiceRequest, String.class);
            return 90;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Invoice API error: " + e.getMessage());
        }
	}

}
