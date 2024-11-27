package com.price.service.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.price.service.InvoiceRepository;
import com.price.service.dto.InputRequest;
import com.price.service.dto.InvoiceRequest;
import com.price.service.entity.Invoice;
import com.price.service.exception.CustomException;
import com.price.service.service.CalculateService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CalculateServiceImpl implements CalculateService {

	
	private final String invoiceUrl = "http://localhost:8081/invoice";
	
	@Autowired
    private InvoiceRepository invoiceRepository;


	private RestTemplate restTemplate;

	public CalculateServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@CircuitBreaker(name = "Price-Service", fallbackMethod = "fallbackMethod")
	public String processPaymentInvoice(InputRequest inputRequestDto) {

		// Calculate total base price from input
		int totalBasePrice = inputRequestDto.billingLines().stream()
				.mapToInt(billingLine -> new BigDecimal(billingLine.price_information().basePrice().amount()).intValue())
				.sum();
		
		try {
			InvoiceRequest invoiceRequest = new InvoiceRequest(Integer.toString(totalBasePrice));

			String postForObject = restTemplate.postForObject(invoiceUrl, invoiceRequest, String.class);
			
			saveToRepo(postForObject,totalBasePrice);
			
			
			return postForObject;
			
		} catch (CustomException e) {
			
			throw new CustomException("Invoice API error: " + e.getMessage());
		}
	}

	private void saveToRepo(String postForObject, int totalBasePrice) {
		
		 Invoice invoice = new Invoice();
		 invoice.setInvoiceId(postForObject);
		 invoice.setTotalAmount(totalBasePrice);
		 invoiceRepository.save(invoice);
		
	}

	public String fallbackMethod(Exception ex) {
		return "Invoice service is unavailable, please try again later.";
	}

	@Override
	public List<Invoice> getAll() {
		// TODO Auto-generated method stub
		return invoiceRepository.findAll();
	}
	
}
