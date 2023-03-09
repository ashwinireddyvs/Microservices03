package com.practice.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	@Retry(name = "sample-api",fallbackMethod = "hardCodedResponse")
	
	//allowing 10000 calls for 10seconds to the sample-api
	@RateLimiter(name="default")
	@Bulkhead(name = "sample-api")
	
	public String sampleApi() {
		logger.info("************Sample api: recieved call*************");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-api", String.class);
		return forEntity.getBody();
	}
	
	//states of circuit breaker closed,open and halfopen 
	@GetMapping("/sample-api-circuitBreaker")
	@CircuitBreaker(name = "default",fallbackMethod = "hardCodedResponse")
	
	//custom no. of calls for  the sample-api
	@RateLimiter(name="sample-api-circuitBreaker")
	@Bulkhead(name = "default")
	public String sampleApiCircuitBreaker() {
		logger.info("************Sample api: recieved call*************");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-api", String.class);
		return forEntity.getBody();
	}
	
	public String hardCodedResponse(Exception exception) {
		return "Fallback response";
		
	}
}
