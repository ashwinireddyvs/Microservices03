package com.practice.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverion calculateCurrencyConversion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		HashMap<String,String> uriVariables = new HashMap<String,String>();
		uriVariables.put("from",from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConverion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConverion.class,uriVariables);
		CurrencyConverion currencyConverion = responseEntity.getBody();
		System.out.println(currencyConverion);
		
		
		
		return new CurrencyConverion(
				currencyConverion.getId(),
				from,to,quantity,
				currencyConverion.getConversionMultiple(),
				quantity.multiply(currencyConverion.getConversionMultiple()),currencyConverion.getEnvironment()+" rest template");
	}
	
	

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverion calculateCurrencyConversionFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		
		CurrencyConverion currencyConverion = currencyExchangeProxy.retrieveExchangeValue(from, to);
		currencyConverion.setId(currencyConverion.getId());
		currencyConverion.setQuantity(quantity);
		currencyConverion.setConversionMultiple(currencyConverion.getConversionMultiple());
		currencyConverion.setTotalCalculatedAmount(quantity.multiply(currencyConverion.getConversionMultiple()));
		currencyConverion.setEnvironment(currencyConverion.getEnvironment()+" Feign");
		
		return currencyConverion;
	}
}
