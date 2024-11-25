package com.price.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.price.service.dto.InputRequest;
import com.price.service.service.impl.CalculateServiceImpl;

@RestController
public class PriceController {
	
	@Autowired
	private CalculateServiceImpl calculateServiceImpl;
	
	@GetMapping("/welcome")
	public String test() {
		
		return "Use JWT TOKEN FOR ACCESS - calculate API";
	}
	
	
	@PostMapping("/paymentService")
    public ResponseEntity<InputRequest> calculatePrice(@RequestBody InputRequest inputRequestDto) {
   
        
		calculateServiceImpl.processPaymentInvoice(inputRequestDto);
		
        return new ResponseEntity<>(inputRequestDto, HttpStatus.OK);
    }

}
