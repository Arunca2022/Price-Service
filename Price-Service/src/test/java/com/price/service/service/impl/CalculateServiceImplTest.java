package com.price.service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.price.service.dto.BillingHeader;
import com.price.service.dto.BillingLine;
import com.price.service.dto.InputRequest;
import com.price.service.dto.InvoiceRequest;
import com.price.service.entity.Invoice;
import com.price.service.repo.InvoiceRepository;

@SpringBootTest
class CalculateServiceImplTest<S> {

	@InjectMocks
	private CalculateServiceImpl calculateServiceImpl;

	@Mock
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@Mock
	private InvoiceRepository invoiceRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		restTemplate = new RestTemplate();

		// Create MockRestServiceServer that mocks the RestTemplate
		mockServer = MockRestServiceServer.createServer(restTemplate);

	}

	@Test
	void testProcessPaymentInvoice_Success() {

		InputRequest inputRequest = new InputRequest(new BillingHeader("", "", ""), new ArrayList<BillingLine>());
		final String invoiceUrl = "http://localhost:8081/invoice";
		InvoiceRequest invoiceRequest = new InvoiceRequest("1000");
		mockServer.expect(requestTo(invoiceUrl)).andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("invoice123", MediaType.ALL));
		Invoice invoice = new Invoice();
		invoice.setInvoiceId("Dummy");
		invoice.setTotalAmount(2000);
		when(invoiceRepository.save(invoice)).thenReturn(invoice);
		String expected = null;
		String invoiceId = calculateServiceImpl.processPaymentInvoice(inputRequest);

		assertEquals(expected, invoiceId);
	}

	@Test
	void testGetAllInvoices() {

		List<Invoice> invoices = Arrays.asList(new Invoice(), new Invoice());
		when(invoiceRepository.findAll()).thenReturn(invoices);

		List<Invoice> result = calculateServiceImpl.getAll();

		assertEquals(2, result.size());

	}

}
