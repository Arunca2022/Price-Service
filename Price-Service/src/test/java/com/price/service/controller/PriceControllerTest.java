/*
 * package com.price.service.controller;
 * 
 * import static org.mockito.ArgumentMatchers.any; import static
 * org.mockito.Mockito.mock; import static org.mockito.Mockito.times; import
 * static org.mockito.Mockito.verify; import static org.mockito.Mockito.when;
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 * 
 * import java.nio.file.Files; import java.util.ArrayList; import
 * java.util.List;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.MockitoAnnotations; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.core.io.ClassPathResource; import
 * org.springframework.security.authentication.AuthenticationManager; import
 * org.springframework.security.authentication.BadCredentialsException; import
 * org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.test.web.servlet.setup.MockMvcBuilders;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * com.price.service.config.JwtService; import
 * com.price.service.dto.AuthRequest; import
 * com.price.service.dto.BillingHeader; import
 * com.price.service.dto.BillingLine; import com.price.service.dto.InputRequest;
 * import com.price.service.entity.Invoice; import
 * com.price.service.service.impl.CalculateServiceImpl;
 * 
 * @SpringBootTest public class PriceControllerTest {
 * 
 * @Autowired private MockMvc mockMvc; // Spring will inject MockMvc
 * automatically
 * 
 * @Mock // Mocking the services that the controller depends on private
 * CalculateServiceImpl calculateServiceImpl;
 * 
 * @Mock private JwtService jwtService;
 * 
 * @Mock private AuthenticationManager authenticationManager;
 * 
 * @Autowired private ObjectMapper objectMapper; // Injecting ObjectMapper to
 * serialize request bodies
 * 
 * @BeforeEach void setUp() { // Mocking and setup happens here if required
 * mockMvc = MockMvcBuilders.standaloneSetup(PriceController).build(); }
 * 
 * @Test void testWelcome() throws Exception { mockMvc.perform(get("/welcome"))
 * .andExpect(status().isOk()) .andExpect(content().
 * string("Use '/tokenGenerate' API for JWT TOKEN Generation ")); }
 * 
 * 
 * 
 * 
 * 
 * 
 * void testCalculatePrice() throws Exception {
 * 
 * ClassPathResource resource = new ClassPathResource("data/" +
 * "inputRequest.json"); byte[] jsonData =
 * Files.readAllBytes(resource.getFile().toPath()); InputRequest inputRequest =
 * objectMapper.readValue(jsonData, InputRequest.class);
 * 
 * 
 * // Mocking the response of the service method
 * when(calculateServiceImpl.processPaymentInvoice(inputRequest)).
 * thenReturn("Invoice processed");
 * 
 * mockMvc.perform(post("/paymentService") .contentType("application/json")
 * .content("{\"amount\": 100}")) .andExpect(status().isOk())
 * .andExpect(content().string("Invoice processed"));
 * 
 * // Verify the service method is called verify(calculateServiceImpl,
 * times(1)).processPaymentInvoice(inputRequest); }
 * 
 * 
 * void testGetAllData() throws Exception { // Prepare mock data List<Invoice>
 * invoices = new ArrayList<>(); Invoice invoice = new Invoice();
 * invoice.setId(1L); invoice.setInvoiceId("INV001");
 * invoice.setTotalAmount(200); invoices.add(invoice);
 * 
 * // Mocking the response from the service
 * when(calculateServiceImpl.getAll()).thenReturn(invoices);
 * 
 * mockMvc.perform(get("/getAllData")) .andExpect(status().isOk())
 * .andExpect(jsonPath("$[0].invoiceId").value("INV001"))
 * .andExpect(jsonPath("$[0].totalAmount").value(200));
 * 
 * // Verify the service method is called verify(calculateServiceImpl,
 * times(1)).getAll(); }
 * 
 * 
 * void testAuthenticateAndGetToken() throws Exception { String username =
 * "user"; String password = "password";
 * 
 * AuthRequest authRequest = new AuthRequest(username, password);
 * 
 * Authentication authentication = mock(Authentication.class);
 * when(authenticationManager.authenticate(any(
 * UsernamePasswordAuthenticationToken.class))) .thenReturn(authentication);
 * 
 * // Mock the JWT token generation
 * when(jwtService.generateToken(username)).thenReturn("jwt-token");
 * 
 * mockMvc.perform(post("/tokenGenerate") .contentType("application/json")
 * .content("{\"username\": \"user\", \"password\": \"password\"}"))
 * .andExpect(status().isOk()) .andExpect(content().string("jwt-token"));
 * 
 * // Verify the service and authentication manager
 * verify(authenticationManager, times(1))
 * .authenticate(any(UsernamePasswordAuthenticationToken.class));
 * verify(jwtService, times(1)).generateToken(username); }
 * 
 * 
 * void testAuthenticateInvalidUser() throws Exception { String username =
 * "invalidUser"; String password = "invalidPassword";
 * 
 * AuthRequest authRequest = new AuthRequest(username, password);
 * 
 * when(authenticationManager.authenticate(any(
 * UsernamePasswordAuthenticationToken.class))) .thenThrow(new
 * BadCredentialsException("Invalid username or password"));
 * 
 * mockMvc.perform(post("/tokenGenerate") .contentType("application/json")
 * .content("{\"username\": \"invalidUser\", \"password\": \"invalidPassword\"}"
 * )) .andExpect(status().isUnauthorized()) .andExpect(result ->
 * result.getResolvedException().getClass()
 * .equals(BadCredentialsException.class)); } }
 */