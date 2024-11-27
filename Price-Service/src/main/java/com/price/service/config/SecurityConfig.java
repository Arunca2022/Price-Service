package com.price.service.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.price.service.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	
	private final  JwtAuthFilter jwtAuthFilter;
	
	
	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider)
            throws Exception {
	    return http
	            .authorizeHttpRequests(authz -> authz
	                    .requestMatchers("/tokenGenerate", "/welcome").permitAll() // Allow unauthenticated access to these paths
	                    .requestMatchers("/paymentService","/getAllData").authenticated() // Require authentication for "/paymentService"
	            )
	            .httpBasic(withDefaults()) // Enable HTTP Basic Authentication
	            .csrf(csrf -> csrf.disable()) // Disable CSRF protection (often necessary for stateless APIs)
	            .sessionManagement(session -> session
	                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session policy (important for REST APIs)
	            )
	            .authenticationProvider(authenticationProvider) // Add the custom AuthenticationProvider to the HTTP security
	            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
	}


    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

    @Bean
    UserDetailsService userDetailsService() {
		// Use BCryptPasswordEncoder for password encoding
		PasswordEncoder passwordEncoder = passwordEncoder();

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

		// Create users with encoded passwords
		manager.createUser(User.builder().username("user1").password(passwordEncoder.encode("password")) // Encoded
																											// password
				.roles("USER").build());

		manager.createUser(User.builder().username("admin").password(passwordEncoder.encode("password")) // Encoded
																											// password
				.roles("ADMIN").build());

		return manager;
	}

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                               PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService); // Set the UserDetailsService
		provider.setPasswordEncoder(passwordEncoder); // Set the PasswordEncoder to match the password
		return provider; // Return the AuthenticationProvider
	}

}
