package com.price.service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.price.service.config.JwtService;
import com.price.service.dto.AuthRequest;
import com.price.service.dto.InputRequest;
import com.price.service.entity.Invoice;
import com.price.service.service.impl.CalculateServiceImpl;

@RestController
public class PriceController {

	private final CalculateServiceImpl calculateServiceImpl;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public PriceController(CalculateServiceImpl calculateServiceImpl, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.calculateServiceImpl = calculateServiceImpl;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/welcome")
	public String test() {

		return "Use '/tokenGenerate' API for JWT TOKEN Generation ";
	}

	@PostMapping("/paymentService")
	public ResponseEntity<String> calculatePrice(@RequestBody InputRequest inputRequestDto) throws Exception{

		String processPaymentInvoice = calculateServiceImpl.processPaymentInvoice(inputRequestDto);

		return new ResponseEntity<>(processPaymentInvoice, HttpStatus.OK);
	}
	
	@GetMapping("/getAllData")
	public ResponseEntity<List<Invoice>> getAllData(){
		
		List<Invoice> allData = calculateServiceImpl.getAll();
		
		if (allData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allData); 
		
	}

	@PostMapping("/tokenGenerate")
	public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

		if (authentication.isAuthenticated()) {

			String token = jwtService.generateToken(authRequest.username());
			return ResponseEntity.ok(token);

		} else {

			throw new BadCredentialsException("Invalid username or password");
		}
	}

}
